package decompose.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackDispatcher
import decompose.home.HomeScreenComponent
import decompose.landing.LandingComponent

interface RootComponent {
    val routerState: Value<ChildStack<*, RootChild>>
    val backDispatcher: BackDispatcher

    sealed class RootChild {
        data class LandingRoot(val landingComponent: LandingComponent): RootChild()
        data class MainRoot(val mainComponent: HomeScreenComponent): RootChild()
    }
}