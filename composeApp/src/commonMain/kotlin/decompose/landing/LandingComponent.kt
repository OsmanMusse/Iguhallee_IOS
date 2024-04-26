package decompose.landing

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface LandingComponent {

    val routerState: Value<ChildStack<*, LandingChild>>

    sealed class LandingChild {
        data class SplashChild(val component: SplashComponent) :LandingChild()
        data class OnboardingChild(val component: OnboardingComponent): LandingChild()
    }
}