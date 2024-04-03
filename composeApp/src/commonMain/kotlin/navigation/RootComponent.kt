package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.arkivanov.essenty.backhandler.BackHandler
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    val backDispatcher : BackDispatcher = (backHandler as? BackDispatcher) ?: BackDispatcher()

    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.HomeScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    fun onBack(){
        navigation.pop()
    }
    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child{
        return when(config){
            is Configuration.HomeScreen -> Child.HomeScreen(
                HomeScreenComponent(
                    componentContext = context,
                    onNavigateToScreenB = { navigation.pushNew(Configuration.ScreenB(it))}
                )
            )
            is Configuration.ScreenB -> Child.ScreenB(
               PostDetailScreenComponent(
                    text = config.text,
                    onGoBack = { onBack() },
                    componentContext = context
                )
            )
        }
    }

    sealed class Child {
        data class HomeScreen(val component: HomeScreenComponent): Child()
        data class ScreenB(val component: PostDetailScreenComponent): Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object HomeScreen: Configuration()
        @Serializable
        data class ScreenB(val text: String): Configuration()
    }


}
