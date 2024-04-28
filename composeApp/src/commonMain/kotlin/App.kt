
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.essenty.backhandler.BackHandler
import decompose.root.RootComponent
import io.github.alexzhirkevich.cupertino.decompose.NativeChildren
import io.github.alexzhirkevich.cupertino.decompose.cupertinoPredictiveBackAnimation
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


