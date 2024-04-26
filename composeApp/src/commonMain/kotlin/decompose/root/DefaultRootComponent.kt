package decompose.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackDispatcher
import decompose.home.HomeScreenComponent
import decompose.landing.DefaultLandingComponent
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val homeScreenFactory: HomeScreenComponent.Factory,
): RootComponent, ComponentContext by componentContext {


    init {
        println("HELLO ROOT COMPONENT CREATED === ")
    }


    private val rootNavigation = StackNavigation<RootConfig>()


    override val backDispatcher: BackDispatcher =
        (backHandler as? BackDispatcher) ?: BackDispatcher()


    // Internal logic of decompose that Manages the navigation and the navigation stack
    override val routerState: Value<ChildStack<*, RootComponent.RootChild>> =
        childStack(
            source = rootNavigation,
            serializer = RootConfig.serializer(),
            initialConfiguration = RootConfig.Main,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    private fun createChild(
        config: RootConfig,
        context: ComponentContext
    ): RootComponent.RootChild{
        return when(config){
            is RootConfig.Landing -> RootComponent.RootChild.LandingRoot(
                DefaultLandingComponent(context)
            )
            is RootConfig.Main -> RootComponent.RootChild.MainRoot(
                homeScreenFactory.create(componentContext = context, onNavigateTo = {})
            )
        }
    }



    @Serializable
    private sealed interface RootConfig {
        @Serializable
        data object Landing : RootConfig

        @Serializable
        data object Main : RootConfig
    }




}