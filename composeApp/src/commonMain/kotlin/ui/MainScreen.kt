package ui


import androidx.compose.runtime.Composable
import decompose.home.HomeScreenComponent
import ui.tabs.BottomNavigationView


@Composable
  fun MainScreen(component: HomeScreenComponent) {
    BottomNavigationView(component)
  }
