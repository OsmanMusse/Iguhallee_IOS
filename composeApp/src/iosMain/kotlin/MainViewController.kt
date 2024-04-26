
import androidx.compose.ui.window.ComposeUIViewController
import androidx.compose.runtime.remember
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.decompose.extensions.compose.jetbrains.PredictiveBackGestureOverlay
import com.arkivanov.essenty.backhandler.BackDispatcher
import decompose.root.DefaultRootComponent
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import org.koin.compose.getKoin


fun MainViewController(backDispatcher: BackDispatcher) = ComposeUIViewController {



    println("Running root 1")
   val getKoinA = getKoin()

    println("Running root 2")

    PredictiveBackGestureOverlay(
        backDispatcher = backDispatcher,
        backIcon = null,
        modifier = Modifier.fillMaxSize(),
        onClose = { println("ON CLOSE CALLED ===") }
    ){
        val root = remember {
            DefaultRootComponent(
                componentContext = DefaultComponentContext(lifecycle = LifecycleRegistry()),
                homeScreenFactory = getKoinA.get()
            )
        }

        App(root)
    }





}
