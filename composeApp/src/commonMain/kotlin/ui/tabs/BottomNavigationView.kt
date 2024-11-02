package ui.tabs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import decompose.home.HomeScreenComponent
import io.github.alexzhirkevich.cupertino.PresentationStyle
import io.github.alexzhirkevich.cupertino.decompose.cupertinoPredictiveBackAnimation
import io.github.alexzhirkevich.cupertino.rememberCupertinoSheetState
import screens.details.PostDetailScreen
import ui.selectlocation.SelectLocationScreen
import ui.tabs.home.bottomsheet.CupertinoBottomSheetScaffold

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun BottomNavigationView(
    component: HomeScreenComponent,
) {

    var bottomSheetState = rememberCupertinoSheetState(presentationStyle = PresentationStyle.Fullscreen)

    Children(
        stack = component.fullscreenStack,
        modifier = Modifier.fillMaxSize(),
        animation = cupertinoPredictiveBackAnimation(
            backHandler = component.backHandler,
            onBack = { component.onBackPress() }
        ),
    ) {
        when (val child = it.instance) {
            is HomeScreenComponent.Child.None -> Unit
            is HomeScreenComponent.Child.TabChild -> {
                CupertinoBottomSheetScaffold(bottomSheetState,child)
            }
            is HomeScreenComponent.Child.PostScreen -> PostDetailScreen(child.component)
            is HomeScreenComponent.Child.SelectLocationScreen -> SelectLocationScreen(child.component)
        }
    }

}





