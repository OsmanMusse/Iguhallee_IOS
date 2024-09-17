package decompose.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import data.post.PostDetailState
import domain.repository.post.PostRepository
import domain.repository.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


class  PostDetailComponent (
    private val postRepo: PostRepository,
    private val userRepo: UserRepository,
    val postID: Long,
    private val onBackPressed: () -> Unit,
    componentContext: ComponentContext
): ComponentContext by componentContext {


    private val backCallback = BackCallback{
        println("BACK BTN PRESS CALLBACK ===")
    }
    private val modalNavigation = SlotNavigation<DialogConfig>()



    var modal = childSlot(
        source = modalNavigation,
        serializer = DialogConfig.serializer(),
        handleBackButton = true
    ){ config, childComponentContext ->
        DefaultPagerModal(
            componentContext = childComponentContext,
            onDismissClicked = { dismissModal() }
        )
    }

    private val scope = coroutineScope(CoroutineScope(Dispatchers.Main).coroutineContext + SupervisorJob())

    private val _state = MutableValue(PostDetailState())
    val state: Value<PostDetailState> = _state

    init {
        setupBackHandler()
        getPost(postID)
    }


    private fun setupBackHandler(){
        backCallback.isEnabled = false
        backHandler.register(backCallback)
    }
    fun showPagerModal(){
        println("MODAL ACTIVATE CALLED ===")
        modalNavigation.activate(DialogConfig(""))
        backCallback.isEnabled = true
    }

    private fun dismissModal(){
        // delay required for modal to not interrupt animation sliding down
        scope.launch {
            delay(500)
            modalNavigation.dismiss()
            backCallback.isEnabled = false
        }
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

     fun navigateBack() = onBackPressed()


    @Serializable
    data class DialogConfig(
        val message: String,
    )

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
            onBackPressed = onGoBack,
            postRepo = postRepo,
            userRepo = userRepo
        )
    }
}