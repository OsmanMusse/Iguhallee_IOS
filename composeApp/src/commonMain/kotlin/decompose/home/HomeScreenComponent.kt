package decompose.home

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandler
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import decompose.detail.PostDetailComponent
import decompose.location.SelectLocationComponent
import decompose.tab.TabComponent

interface HomeScreenComponent: BackHandlerOwner {
    val fullscreenStack: Value<ChildStack<*, Child>>
    fun onBackPress()


    sealed class Child {
        data object None: Child()
        class TabChild(val component: TabComponent): Child()
        class PostScreen(val component: PostDetailComponent): Child()
        class SelectLocationScreen(val component: SelectLocationComponent): Child()
    }
}