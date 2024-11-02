package ui.tabs.home.bottomsheet

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import components.CupertinoBottomNavigationBar
import decompose.home.HomeScreenComponent
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetContent
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffold
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.CupertinoSheetState
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import ui.tabs.TabView

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun CupertinoBottomSheetScaffold(
    bottomSheetState: CupertinoSheetState,
    child: HomeScreenComponent.Child.TabChild,
) {
    CupertinoBottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = CupertinoBottomSheetScaffoldState(bottomSheetState),
        bottomBar = { CupertinoBottomNavigationBar(bottomSheetState, child) },
        sheetSwipeEnabled = false,
        sheetContent = {
            CupertinoBottomSheetContent(
                modifier = Modifier.fillMaxSize(),
                topBar = { CupertinoBottomSheetTopAppBar(bottomSheetState) },
                content = { paddingValues -> CuperintoBottomSheetContent(paddingValues) }
            )
        },
    ) {
        val tabStack by child.component.stack.subscribeAsState()
        println("CURRENT TAB STACK == $tabStack")
        Children(stack = tabStack) {
            TabView(child.component)
        }
    }
}