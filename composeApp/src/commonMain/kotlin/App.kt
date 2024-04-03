import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimator
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import domain.model.PostStatus
import io.github.alexzhirkevich.cupertino.decompose.NativeChildren
import io.github.alexzhirkevich.cupertino.decompose.cupertinoPredictiveBackAnimation
import navigation.RootComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import screens.MainScreen.HomeScreen
import screens.PostDetailScreen.PostDetailScreen


@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App(rootComponent: RootComponent) {
    val childStack by rootComponent.childStack.subscribeAsState()

    NativeChildren(
        stack = rootComponent.childStack,
        modifier = Modifier.fillMaxSize(),
        backDispatcher = rootComponent.backDispatcher,
        animation = cupertinoPredictiveBackAnimation(
            backHandler = rootComponent.backDispatcher,
            onBack = { rootComponent.onBack()},
        ),
        content = { child ->
            when(val instance = child.instance){
                is RootComponent.Child.HomeScreen -> HomeScreen(instance.component)
                is RootComponent.Child.ScreenB -> PostDetailScreen(instance.component)
            }
        }

    )
//    Children(
//        stack = childStack,
//        animation = stackAnimation(slide())
//    ){ child ->
//        when(val instance = child.instance){
//            is RootComponent.Child.HomeScreen -> HomeScreen(instance.component)
//            is RootComponent.Child.ScreenB -> PostDetailScreen(instance.component)
//        }
//    }
}