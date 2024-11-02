package decompose.tab


import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import decompose.home.HomeListComponent
import kotlinx.serialization.Serializable
import decompose.tab.account.AccountComponent
import decompose.tab.account.DefaultAccountComponent


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
            handleBackButton = false,
            childFactory = ::child,
        )

    override val stack: Value<ChildStack<*, TabComponent.Child>> get() = _stack

    override fun onTabSelected(tab: TabComponent.Tab){
        val tabConfig = tab.toConfig()
        navigation.bringToFront(tabConfig)
    }


    private fun child(config: Config, componentContext: ComponentContext): TabComponent.Child {
        return when (config) {
            Config.Home ->  createHomeChild(componentContext)
            Config.Account -> createAccountChild(componentContext)
            Config.Post -> createPostChild(componentContext)
            Config.Saved -> createSavedChild(componentContext)
            Config.Settings -> createSettingsChild(componentContext)
        }
    }

    private fun createHomeChild(componentContext: ComponentContext) = TabComponent.Child.Home(homeChild(componentContext))

    private fun createAccountChild(componentContext: ComponentContext) = TabComponent.Child.Account(accountChild(componentContext))

    private fun createPostChild(componentContext: ComponentContext) = TabComponent.Child.Sell(postChild(componentContext))

    private fun createSavedChild(componentContext: ComponentContext) = TabComponent.Child.Saved(savedChild(componentContext))

    private fun createSettingsChild(componentContext: ComponentContext) = TabComponent.Child.Settings(settingsChild(componentContext))

    private fun homeChild(componentContext: ComponentContext): HomeListComponent {
        return homeListFactory.create(
            componentContext = componentContext,
            onPushScreen = { onNavigatePost(it) },
            onPushLocationScreen = { onNavigateLocation(it) }
        )
    }

    private fun accountChild(componentContext: ComponentContext): AccountComponent {
        return DefaultAccountComponent(componentContext)
    }

    private fun postChild(componentContext: ComponentContext): AccountComponent {
        return DefaultAccountComponent(componentContext)
    }

    private fun savedChild(componentContext: ComponentContext): AccountComponent {
        return DefaultAccountComponent(componentContext)
    }

    private fun settingsChild(componentContext: ComponentContext): AccountComponent {
        return DefaultAccountComponent(componentContext)
    }


    private fun TabComponent.Tab.toConfig(): Config = when (this) {
        TabComponent.Tab.Home -> Config.Home
        TabComponent.Tab.Account -> Config.Account
        TabComponent.Tab.Sell -> Config.Post
        TabComponent.Tab.Saved -> Config.Saved
        TabComponent.Tab.Settings -> Config.Settings
    }


    /**
     * Config providing type safe arguments, as well as any kind of dependencies to child components.
     */
    @Serializable
     sealed interface Config: Parcelable {
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
