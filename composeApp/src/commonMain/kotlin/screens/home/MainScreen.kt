package screens.home


import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import io.github.alexzhirkevich.cupertino.PresentationStyle
import io.github.alexzhirkevich.cupertino.rememberCupertinoSheetState
import decompose.home.HomeScreenComponent
import screens.BottomNavigationView




  @Composable
  fun MainScreen(component: HomeScreenComponent) {

    val scope = rememberCoroutineScope()
    var bottomSheetState = rememberCupertinoSheetState(presentationStyle = PresentationStyle.Fullscreen)

    BottomNavigationView(component,scope,bottomSheetState)

  }
