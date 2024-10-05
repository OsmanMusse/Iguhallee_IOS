package decompose.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import decompose.home.DefaultHomeScreenComponent
import decompose.home.HomeScreenComponent
import decompose.landing.DefaultLandingComponent
import decompose.landing.LandingComponent
import decompose.splash.DefaultSplashComponent
import domain.repository.preferences.AppPreferencesRepository
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    private val componentContext: ComponentContext,
    private val appPreferencesRepo: AppPreferencesRepository,
    private val homeScreenFactory: DefaultHomeScreenComponent.Factory,
    private val landingScreenFactory: DefaultLandingComponent.Factory,
    private val splashScreenFactory: DefaultSplashComponent.Factory
): RootComponent, ComponentContext by componentContext {


    init {
        println("HELLO ROOT COMPONENT CREATED === ")
    }


    private val rootNavigation = StackNavigation<RootConfig>()


    /**
     * Use this to retrieve first patch of initial preferences by blocking the thread.
     */
    private val isLocationAssigned = runBlocking { appPreferencesRepo.fetchInitialPreferences().isDefaultLocationSelected }


    override val backDispatcher: BackDispatcher =
        (backHandler as? BackDispatcher) ?: BackDispatcher()


    // Internal logic of decompose that Manages the navigation and the navigation stack
    override val routerState: Value<ChildStack<*, RootComponent.RootChild>> =
        childStack(
            source = rootNavigation,
            serializer = RootConfig.serializer(),
            initialConfiguration = if(isLocationAssigned) RootConfig.Main else RootConfig.Landing,
            handleBackButton = true,
            childFactory = ::createChild,
        )



    override fun navigateToHomeScreen(selectedLocation: String?) {
        if (selectedLocation != null) {
            rootNavigation.replaceCurrent(RootConfig.Main)
        } else {
            rootNavigation.replaceCurrent(RootConfig.Landing)
        }
    }

    private fun createChild(
        config: RootConfig,
        context: ComponentContext
    ): RootComponent.RootChild{
        return when(config){

            is RootConfig.Landing -> {
                RootComponent.RootChild.LandingRoot(
                    landingScreenFactory.create(
                        componentContext = componentContext
                    )
                )
            }

            is RootConfig.Splash -> {
                RootComponent.RootChild.SplashRoot(
                    splashScreenFactory.create(
                        componentContext = componentContext,
                        onNavigateTo = { location ->
                            navigateToHomeScreen(location)
                        }
                    )
                )
            }

            is RootConfig.Main -> {
                RootComponent.RootChild.MainRoot(
                    homeScreenFactory.create(componentContext = context, onNavigateTo = {})
                )
            }
        }
    }



    @Serializable
    private sealed interface RootConfig {
        @Serializable
        data object Landing : RootConfig

        data object Splash: RootConfig

        @Serializable
        data object Main : RootConfig
    }

}