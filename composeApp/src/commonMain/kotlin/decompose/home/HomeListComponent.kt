package decompose.home


import app.cash.paging.PagingData
import app.cash.paging.cachedIn
import app.cash.paging.map
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import domain.model.HomeScreenState
import domain.model.Post
import domain.repository.PostRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeListComponent(
    private val repo: PostRepository,
    componentContext: ComponentContext,
    private val onPushScreen: (String) -> Unit
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
    }

    fun unsavePost(postID: Long){
        componentScope.launch {
            repo.deleteSavedPost(postID)
        }
    }

     fun savePost(postID: Long){
        componentScope.launch {
            repo.saveLikedPost(postID)
            repo.getAllLikedPosts().collect {
                println("BOOKMARKED NEW POSTS == ${it}")
            }
        }
    }

    fun goToDetailsScreen(){
        onPushScreen("Go to the details screen")
    }


    private fun retrieveLikedPost(){
        componentScope.launch {
            repo.getAllLikedPosts().collect {
               _state.value = _state.value.copy(bookmarkedPosts = it)
            }
        }
    }
    private fun retrievePosts(){
       componentScope.launch {
           repo.getAllPosts().cachedIn(componentScope).collect { pagingData ->
              _posts.value = pagingData
           }
       }
    }


 class Factory(
    private val repo: PostRepository
 ){
    fun create(
        componentContext: ComponentContext,
        onPushScreen: (String) -> Unit
    ): HomeListComponent = HomeListComponent(
        componentContext = componentContext,
        repo = repo,
        onPushScreen = onPushScreen
    )
  }
 }