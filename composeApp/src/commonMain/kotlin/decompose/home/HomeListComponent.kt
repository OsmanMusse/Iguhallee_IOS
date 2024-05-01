package decompose.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import domain.model.HomeScreenState
import domain.repository.PostRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeListComponent(
    private val repo: PostRepository,
    componentContext: ComponentContext,
    private val onPushScreen: (String) -> Unit
): ComponentContext by componentContext {

    private val _state = MutableValue(HomeScreenState())

    val state: Value<HomeScreenState> get() =  _state


    init {
       println("HOMELIST COMPONENT CREATED")
       retrievePosts()
    }


    fun goToDetailsScreen(){
        onPushScreen("Go to the details screen")
    }

    private fun retrievePosts(){

        coroutineScope().launch {
            _state.value = _state.value.copy(isLoading = true)
            val request = repo.getAllPosts()
            delay(2000)
            _state.value = _state.value.copy(isLoading = false, postList = request)
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