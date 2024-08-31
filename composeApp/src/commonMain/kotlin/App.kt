
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import decompose.root.RootComponent
import org.jetbrains.compose.ui.tooling.preview.Preview
import screens.HomeScreen.MainScreen


@Composable
@Preview
fun App(rootComponent: RootComponent) {

    println("Child Created == 2")

    val childStack = rootComponent.routerState

    Children(
        stack = childStack,
        modifier = Modifier.fillMaxSize(),
        content = { child ->
            println("Child Created == ")
           when(val childCreated = child.instance){
               is RootComponent.RootChild.LandingRoot -> Unit
               is RootComponent.RootChild.MainRoot -> MainScreen(childCreated.mainComponent)
           }
        }
    )
}


