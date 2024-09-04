package decompose.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.serialization.Serializable
import screens.AccountScreen.AccountComponent

class TabComponent(
    componentContext: ComponentContext,
    val tab: Tab,
    private val homeListFactory: HomeListComponent.Factory,
    private val onNavigatePost: (postID:String) -> Unit,
    private val onNavigateLocation: (location: String) -> Unit
): ComponentContext by componentContext {

    init {
        println("HELLO TAB COMPONENT CREATED === ${tab} ")
    }


    fun onLocationChecked(location: String) {
        (_stack.active.instance as Child.Main.Home).component.updateLocation(location)
    }

    private val navigation = StackNavigation<Config>()

    private val _stack =
        childStack(
            source = navigation,
            initialConfiguration = Config.Main(tab = tab),
            handleBackButton = true,
            childFactory = ::child,
        )

    val stack: Value<ChildStack<*, Child>> get() = _stack


    private fun child(config: Config, componentContext: ComponentContext): Child {
        return when (config) {
            is Config.Main ->  {
                println("Main Child Selected === ${config.tab}")
                mainChild(config, componentContext)
            }
            else -> {
                println("${_stack.value} Child Selected ===")
                mainChild (config as Config.Main, componentContext)
            }
        }
    }

    private fun mainChild(config: Config.Main, componentContext: ComponentContext): Child {
        return when (config.tab) {
            Tab.Home -> Child.Main.Home(homeChild(componentContext))
            Tab.Account -> Child.Main.Home(homeChild(componentContext))
            Tab.Post -> Child.Main.Home(homeChild(componentContext))
            Tab.Saved -> Child.Main.Saved(savedChild(componentContext))
            Tab.Settings -> Child.Main.Home(homeChild(componentContext))
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



    // Think of them as your equivalent to viewmodels that each of your screen takes AKA Components
    sealed interface Child {
        sealed interface Main: Child {
            class Home(val component: HomeListComponent): Main
            class Account(val component: AccountComponent): Main
            class Post(val component: AccountComponent): Main
            class Saved(val component: AccountComponent): Main
            class Settings(val component: AccountComponent): Main
        }

    }

    enum class Tab {
        Home, Account, Post, Saved, Settings;
    }


    /// The set of parameters or argument that we pass to a specific screen
    @Serializable
    private sealed interface Config: Parcelable {
        @Serializable
        data class Main(val tab: Tab): Config
    }

    class Factory(
        private val homeListFactory: HomeListComponent.Factory
    ){

        fun create(
            componentContext: ComponentContext,
            tab: Tab,
            onNavigatePost: (postID:String) -> Unit,
            onNavigateLocation: (location: String) -> Unit
        ): TabComponent = TabComponent(
            componentContext = componentContext,
            tab = tab,
            homeListFactory = homeListFactory,
            onNavigatePost = onNavigatePost,
            onNavigateLocation = onNavigateLocation
        )

    }

}