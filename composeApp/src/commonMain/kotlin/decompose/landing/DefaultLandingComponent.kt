package decompose.landing

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

class DefaultLandingComponent(
    componentContext: ComponentContext,
): LandingComponent, ComponentContext by componentContext {

    private val landingNavigation = StackNavigation<LandingConfig>()

    override val routerState: Value<ChildStack<*, LandingComponent.LandingChild>> =
        childStack(
            source = landingNavigation,
            serializer = LandingConfig.serializer(),
            initialStack = { listOf(LandingConfig.Splash) },
            handleBackButton = true,
            childFactory = ::createLandingChild
        )



    private fun createLandingChild(
        config: LandingConfig,
        componentContext: ComponentContext
    ): LandingComponent.LandingChild {
        return when(config){
            LandingConfig.Splash -> LandingComponent.LandingChild.SplashChild(
                SplashComponent(componentContext)
            )
            LandingConfig.Onboarding -> LandingComponent.LandingChild.OnboardingChild(
                OnboardingComponent(componentContext)
            )
        }

    }

    @Serializable
       sealed interface LandingConfig {
        @Serializable
            data object Splash: LandingConfig
        @Serializable
            data object Onboarding: LandingConfig
        }

}