package decompose.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import data.post.PostDetailState
import domain.repository.PostRepository
import domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class  PostDetailComponent (
    private val postRepo: PostRepository,
    private val userRepo: UserRepository,
    val postID: Long,
    private val onGoBack: () -> Unit,
    componentContext: ComponentContext
): ComponentContext by componentContext {

    private val scope = coroutineScope(CoroutineScope(Dispatchers.Main).coroutineContext + SupervisorJob())

    private val _state = MutableValue(PostDetailState())
    val state: Value<PostDetailState> = _state

    init {
        println("POST ID RECIEVED THROUGH THE ARGUEMNT == ${postID}")
        getPost(postID)

    }




     fun bookmarkPost(postID: Long){
         println("BOOKMARKED POST ===")
         scope.launch {
             postRepo.saveLikedPost(postID)
             _state.value = _state.value.copy(isPostBookmarked = true)
         }
    }

     fun unBookmarkPost(postID: Long){
         println("UNBOOKMARKED POST ===")
         scope.launch {
             postRepo.deleteLikedPost(postID)
             _state.value = _state.value.copy(isPostBookmarked = false)
         }
    }
    private fun getSavedPosts() {
        scope.launch {
            postRepo.getAllLikedPosts().collect {
                val postID = state.value.post?.id
                val isPostBookmarked = it.contains(postID)
                _state.value = _state.value.copy(isPostBookmarked = isPostBookmarked)
            }
        }
    }
     private fun getPost(postID:Long){
        scope.launch {
             postRepo.getPost(postID).collect { post ->
                 _state.value = _state.value.copy(post = post, isMainContentLoading = false)
                 getUserProfile("vekMMi4owrfBFNSIpDSsRpY11ym1")
                 getSavedPosts()
             }
        }
    }

    private fun getUserProfile(userID: String){
        _state.value = _state.value.copy(isUserProfileLoading = true)
        scope.launch {
            val userData = userRepo.getUser(userID)
            println("USER INFO === ${userRepo.getUser(userID)}")
            _state.value = _state.value.copy(user = userData, isUserProfileLoading = false)
        }
    }

     fun goBack(){
         onGoBack()
     }


    class Factory(
        private val postRepo: PostRepository,
        private val userRepo: UserRepository
    ){
        fun create(
            componentContext: ComponentContext,
            postID: Long,
            onGoBack: () -> Unit
        ): PostDetailComponent = PostDetailComponent(
            componentContext = componentContext,
            postID =  postID,
            onGoBack = onGoBack,
            postRepo = postRepo,
            userRepo = userRepo
        )
    }
}