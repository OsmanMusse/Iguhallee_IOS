package decompose.home


import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandler
import com.arkivanov.essenty.parcelable.Parcelable
import domain.model.HomeScreenState
import kotlinx.serialization.Serializable
import decompose.detail.PostDetailComponent
import decompose.location.DefaultSelectLocationComponent
import decompose.location.SelectLocationComponent
import decompose.tab.DefaultTabComponent
import decompose.tab.TabComponent


class DefaultHomeScreenComponent(
    private val tabFactory: DefaultTabComponent.Factory,
    private val postDetailFactory: PostDetailComponent.Factory,
    private val componentContext: ComponentContext,
    private val onNavigateTo: (String) -> Unit
):  HomeScreenComponent,ComponentContext by componentContext {

    init {
        println("HI THE H COMPONENT HAS BEEN CREATED")
    }


    private val _state = MutableValue(HomeScreenState())
    val state = _state


    private val mainNavigation = StackNavigation<Config>()


    private val _fullscreenStack =
        childStack(
            source = mainNavigation,
            initialConfiguration = Config.Tabs,
            serializer = Config.serializer(),
            key = "fullscreen",
            handleBackButton = true,
            childFactory = ::createFullScreenChild
        )


    override val fullscreenStack: Value<ChildStack<*, HomeScreenComponent.Child>>
        get() = _fullscreenStack

    override fun onBackPress(){
        println("DO BACK GESTURE 2 ===")
        mainNavigation.pop()
    }




    private fun createFullScreenChild(config: Config, componentContext: ComponentContext): HomeScreenComponent.Child {
        return when(config){
            is Config.None -> HomeScreenComponent.Child.None
            is Config.Tabs -> HomeScreenComponent.Child.TabChild(createTabComponent(config,componentContext))
            is Config.PostScreen -> HomeScreenComponent.Child.PostScreen(postDetailComponent(config,componentContext))
            is Config.LocationScreen -> HomeScreenComponent.Child.SelectLocationScreen(selectLocationComponent(config,componentContext))
        }
    }

    private fun postDetailComponent(config: Config.PostScreen,componentContext: ComponentContext): PostDetailComponent {
        return postDetailFactory.create(
            postID = config.postID.toLong(),
            componentContext = componentContext,
            onGoBack = { mainNavigation.pop() }
        )
    }

    private fun selectLocationComponent(config: Config.LocationScreen,componentContext: ComponentContext): SelectLocationComponent {
        return DefaultSelectLocationComponent(
            componentContext = componentContext,
            currentLocation = config.location,
            onGoBack = { location ->
                mainNavigation.pop {
                    println("GO TO HOMESCREEN WITH SELECTED LOCATION == ${location}")
//                    if (location != null) (tabStack.active.instance).onLocationChecked(location)
                }
            }
        )
    }
    private fun createTabComponent(config: Config, componentContext: ComponentContext): TabComponent {
        return tabFactory.create(
            componentContext = componentContext,
            onNavigatePost = { postID ->
                mainNavigation.push(Config.PostScreen(postID))
            },
            onNavigateLocation = { location ->
                mainNavigation.push(Config.LocationScreen(location))
            }
        )
    }


    class Factory(
        private val tabFactory: DefaultTabComponent.Factory,
        private val postDetailFactory: PostDetailComponent.Factory
    ){
        fun create(
            componentContext: ComponentContext,
            onNavigateTo: (String) -> Unit,
        ) = DefaultHomeScreenComponent(
            componentContext = componentContext,
            onNavigateTo = onNavigateTo,
            tabFactory = tabFactory,
            postDetailFactory = postDetailFactory
        )
    }


    @Serializable
    sealed interface Config: Parcelable {
        @Serializable
        data object Tabs: Config
        @Serializable
        data object None: Config
        @Serializable
        data class PostScreen(val postID: String): Config

        @Serializable
        data class LocationScreen(val location: String): Config
    }

}
