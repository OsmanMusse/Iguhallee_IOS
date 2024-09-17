
import androidx.compose.ui.window.ComposeUIViewController
import androidx.compose.runtime.remember
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.decompose.extensions.compose.jetbrains.PredictiveBackGestureOverlay
import com.arkivanov.essenty.backhandler.BackDispatcher
import decompose.root.DefaultRootComponent
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.lifecycle.ApplicationLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.compose.getKoin
import platform.UIKit.UIApplication


@OptIn(ExperimentalDecomposeApi::class)
fun MainViewController(backDispatcher: BackDispatcher) = ComposeUIViewController {

   val getKoinA = getKoin()

    PredictiveBackGestureOverlay(
        backDispatcher = backDispatcher,
        backIcon = null,
        modifier = Modifier.fillMaxSize(),
        onClose = { println("ON CLOSE CALLED ===") }
    ){
        val root = remember {
            DefaultRootComponent(
                componentContext = DefaultComponentContext(lifecycle = ApplicationLifecycle(), backHandler = backDispatcher),
                appPreferencesRepo = getKoinA.get(),
                homeScreenFactory = getKoinA.get(),
                splashScreenFactory = getKoinA.get(),
                landingScreenFactory = getKoinA.get()
            )
        }

        App(root)
    }

}
