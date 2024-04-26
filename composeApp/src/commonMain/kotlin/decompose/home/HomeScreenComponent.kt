package decompose.home


import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.parcelable.Parcelable
import domain.model.HomeScreenState
import kotlinx.serialization.Serializable
import navigation.PostDetailComponent


class HomeScreenComponent(
    private val tabFactory: TabComponent.Factory,
    private val componentContext: ComponentContext,
    private val onNavigateTo: (String) -> Unit
):ComponentContext by componentContext {


    init {
        println("HI THE H COMPONENT HAS BEEN CREATED")
    }


    private val _state = MutableValue(HomeScreenState())
    val state = _state


    private val mainNavigation = StackNavigation<MainConfig>()
    private val tabNavigation = StackNavigation<TabConfig>()

//    val backDispatcher : BackDispatcher = (backHandler as? BackDispatcher) ?: BackDispatcher()


    private val _fullscreenStack =
        childStack(
            source = mainNavigation,
            initialConfiguration = MainConfig.None,
            serializer = MainConfig.serializer(),
            key = "fullscreen",
            handleBackButton = true,
            childFactory = ::createFullScreenChild
        )
    private val _tabStack =
        childStack(
            source = tabNavigation,
            initialConfiguration = TabConfig(TabComponent.Tab.Home),
            key = "tabs",
            handleBackButton = false,
            childFactory = ::createTab,
        )


    val fullScreenStack: Value<ChildStack<*, FullScreenChild>> get() = _fullscreenStack
    val tabStack: Value<ChildStack<*, TabComponent>> get() = _tabStack



    fun onBack(){
        println("GO BACK TO HOMESCREEN 1 == ${_fullscreenStack.active}")
        mainNavigation.pop()
        println("GO BACK TO HOMESCREEN 2 == ${_fullscreenStack.active}")
    }
    fun onTabSelected(tab: TabComponent.Tab){
        println("Tab selected == $tab")
        tabNavigation.navigate { stack ->
            stack.filterNot { it.tab == tab } + TabConfig(tab)
        }
    }


    private fun createFullScreenChild(config: MainConfig, componentContext: ComponentContext): FullScreenChild {
        return when(config){
            is MainConfig.None -> FullScreenChild.None
            is MainConfig.PostScreen -> FullScreenChild.PostScreen(postDetailComponent(config,componentContext))
        }
    }

    private fun postDetailComponent(config: MainConfig.PostScreen,componentContext: ComponentContext): PostDetailComponent {
        return PostDetailComponent(
            config.postID,
            componentContext = componentContext,
            onGoBack = { println("Go Back to HomeScreen Component ===") }
        )
    }
    private fun createTab(config: TabConfig, componentContext: ComponentContext): TabComponent {
        return tabFactory.create(
            componentContext = componentContext,
            tab = config.tab,
            onNavigatePost = {
                println("AT GRAND PARENT NOW, EXECUTE EVENT")
                mainNavigation.push(MainConfig.PostScreen(""))
            }
        )
    }


    class Factory(
        private val tabFactory: TabComponent.Factory
    ){
        fun create(
            componentContext: ComponentContext,
            onNavigateTo: (String) -> Unit,
        ) = HomeScreenComponent(
            componentContext = componentContext,
            onNavigateTo = onNavigateTo,
            tabFactory = tabFactory
        )
    }



    sealed interface FullScreenChild {
        object None: FullScreenChild
        class PostScreen(val postDetailsComponent: PostDetailComponent): FullScreenChild
    }
    @Serializable
    sealed interface MainConfig: Parcelable {
        @Serializable
        data object None: MainConfig
        @Serializable
        data class PostScreen(val postID: String): MainConfig
    }

    @Serializable
    private data class TabConfig(val tab: TabComponent.Tab): Parcelable
}
