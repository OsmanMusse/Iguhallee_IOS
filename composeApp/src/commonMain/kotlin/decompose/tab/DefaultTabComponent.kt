package decompose.tab

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import decompose.home.HomeListComponent
import kotlinx.serialization.Serializable
import screens.AccountScreen.AccountComponent

class DefaultTabComponent(
    componentContext: ComponentContext,
    private val homeListFactory: HomeListComponent.Factory,
    private val onNavigatePost: (postID:String) -> Unit,
    private val onNavigateLocation: (location: String) -> Unit
): TabComponent,ComponentContext by componentContext {

    init {
        println("HELLO TAB COMPONENT CREATED ===")
    }


    fun onLocationChecked(location: String) {
        (_stack.active.instance as TabComponent.Child.Home).component.updateLocation(location)
    }

    private val navigation = StackNavigation<Config>()

    private val _stack =
        childStack(
            source = navigation,
            initialConfiguration = Config.Home,
            handleBackButton = true,
            childFactory = ::child,
        )

    override val stack: Value<ChildStack<*, TabComponent.Child>> get() = _stack

    override fun onTabSelected(tab: TabComponent.Tab){
        println("Tab selected == $tab")

        navigation.navigate { stack ->
             stack.filterNot {  it == Config.Saved } + Config.Saved
        }
    }


    private fun child(config: Config, componentContext: ComponentContext): TabComponent.Child {
        return when (config) {
            is Config.Home ->  {
                println("Main Child Selected === ${config}")
                mainChild(config, componentContext)
            }
            else -> {
                println("${_stack.value} Child Selected ===")
                mainChild (config, componentContext)
            }
        }
    }

    private fun mainChild(config: Config, componentContext: ComponentContext): TabComponent.Child {
        return when (config) {
            Config.Home -> TabComponent.Child.Home(homeChild(componentContext))
            Config.Account -> TabComponent.Child.Home(homeChild(componentContext))
            Config.Post -> TabComponent.Child.Home(homeChild(componentContext))
            Config.Saved -> TabComponent.Child.Saved(savedChild(componentContext))
            Config.Settings -> TabComponent.Child.Home(homeChild(componentContext))
        }
    }

    private fun homeChild(componentContext: ComponentContext): HomeListComponent {
        return homeListFactory.create(
            componentContext,
            onPushScreen = { onNavigatePost(it) },
            onPushLocationScreen = { onNavigateLocation(it) }
        )
    }

    private fun savedChild(componentContext: ComponentContext): AccountComponent {
        return AccountComponent(componentContext)
    }




    /**
     * Config providing type safe arguments, as well as any kind of dependencies to child components.
     */
    @Serializable
    private sealed interface Config: Parcelable {
        @Serializable
       data object Home: Config
        @Serializable
        data object Account: Config
        @Serializable
        data object Post: Config
        @Serializable
        data object Saved: Config
        @Serializable
        data object Settings: Config
    }

    class Factory(
        private val homeListFactory: HomeListComponent.Factory
    ){

        fun create(
            componentContext: ComponentContext,
            onNavigatePost: (postID:String) -> Unit,
            onNavigateLocation: (location: String) -> Unit
        ): TabComponent = DefaultTabComponent(
            componentContext = componentContext,
            homeListFactory = homeListFactory,
            onNavigatePost = onNavigatePost,
            onNavigateLocation = onNavigateLocation
        )

    }

}
