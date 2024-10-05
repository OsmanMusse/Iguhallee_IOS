package decompose.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackDispatcher
import decompose.home.HomeScreenComponent
import decompose.landing.LandingComponent
import decompose.splash.SplashComponent

interface RootComponent {
    val routerState: Value<ChildStack<*, RootChild>>
    val backDispatcher: BackDispatcher

    fun navigateToHomeScreen(selectedLocation: String?)

    sealed class RootChild {
        data class LandingRoot(val landingComponent: LandingComponent): RootChild()
        data class MainRoot(val mainComponent: HomeScreenComponent): RootChild()
        data class SplashRoot(val splashComponent: SplashComponent): RootChild()
    }
}