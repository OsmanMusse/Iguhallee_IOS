package decompose.home


import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import domain.model.HomeScreenState
import domain.model.Post
import domain.repository.post.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeListComponent(
    private val repo: PostRepository,
    componentContext: ComponentContext,
    private val onPushScreen: (String) -> Unit,
    private val onPushLocationScreen: (String) -> Unit
): ComponentContext by componentContext {

    private val _state = MutableValue(HomeScreenState())

    private val componentScope = coroutineScope()
    val state: Value<HomeScreenState> get() =  _state

    // Paging

    private val _posts: MutableStateFlow<PagingData<Post>> = MutableStateFlow(PagingData.empty())
    val posts: StateFlow<PagingData<Post>> = _posts.asStateFlow()


    init {
       println("HOMELIST COMPONENT CREATED")
        retrieveLikedPost()
        retrievePosts()
        observeLikedPosts()
    }


    fun updateLocation(location: String) {
        _state.value = _state.value.copy(currentCity = location)
    }

    fun refreshPosts() =  retrievePosts()

    fun emptyPagingData() {
        _posts.value = PagingData.empty()
    }

    private fun observeLikedPosts(){
        componentScope.launch {
            repo.getAllLikedPosts().collect { newList ->
                _state.value = _state.value.copy(bookmarkedPosts = newList)

            }
        }
    }

    fun unsavePost(postID: Long){
        componentScope.launch {
            repo.deleteLikedPost(postID)
        }
    }

     fun savePost(postID: Long){
        componentScope.launch {
            repo.saveLikedPost(postID)
        }
    }

    fun goToDetailsScreen(postID: Long?) {
        onPushScreen("${postID}")
    }

    fun goToLocationScreen(location: String) {
        onPushLocationScreen(location)
    }


    private fun retrieveLikedPost(){
        componentScope.launch {
            repo.getAllLikedPosts().collect {
               _state.value = _state.value.copy(bookmarkedPosts = it)
            }
        }
    }
    private fun retrievePosts(){
        val location = _state.value.currentCity
        println("CALL RETRIEVE POST === ${location}")
       componentScope.launch {
           repo.getAllPosts(location).cachedIn(componentScope).collect { pagingData ->
              _posts.value = pagingData
           }
       }
    }


 class Factory(
    private val repo: PostRepository
 ){
    fun create(
        componentContext: ComponentContext,
        onPushScreen: (String) -> Unit,
        onPushLocationScreen: (String) -> Unit
    ): HomeListComponent = HomeListComponent(
        componentContext = componentContext,
        repo = repo,
        onPushScreen = onPushScreen,
        onPushLocationScreen = onPushLocationScreen
    )
  }
 }