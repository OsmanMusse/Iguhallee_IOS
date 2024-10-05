package screens


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.ramaas.iguhallee.MR
import decompose.home.HomeScreenComponent
import decompose.tab.TabComponent
import dev.icerock.moko.resources.compose.painterResource
import util.NoRippleTheme
import kotlinx.coroutines.launch
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetContent
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffold
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoNavigationBar
import io.github.alexzhirkevich.cupertino.CupertinoSheetState
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.decompose.cupertinoPredictiveBackAnimation
import kotlinx.coroutines.CoroutineScope
import screens.details.PostDetailScreen
import screens.selectlocation.SelectLocationScreen

@OptIn(ExperimentalCupertinoApi::class, ExperimentalDecomposeApi::class)
 @Composable
 fun BottomNavigationView(
    component: HomeScreenComponent,
    scope: CoroutineScope,
    bottomSheetState: CupertinoSheetState,
 ) {


     Children(
         stack = component.fullscreenStack,
         modifier = Modifier.fillMaxSize(),
         animation = cupertinoPredictiveBackAnimation(
             backHandler = component.backHandler,
             onBack = {
                 println("DO BACK GESTURE ===")
                 component.onBackPress()
             }
         ),
     ) {
         when(val child = it.instance) {
             is HomeScreenComponent.Child.None -> Unit
              is HomeScreenComponent.Child.TabChild -> {
                  CupertinoBottomSheetScaffold(
                      modifier = Modifier.fillMaxSize(),
                      scaffoldState = CupertinoBottomSheetScaffoldState(bottomSheetState),
                      bottomBar = {
                          CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                              CupertinoNavigationBar(isTranslucent = false) {
                                  TabComponent.Tab.values().forEach { specificTab ->
                                      val (title, icon) = when (specificTab) {
                                          TabComponent.Tab.Home -> specificTab.name to MR.images.home_icon_lineal
                                          TabComponent.Tab.Account -> specificTab.name to MR.images.account_icon
                                          TabComponent.Tab.Post -> specificTab.name to MR.images.camera_icon_linear
                                          TabComponent.Tab.Saved -> specificTab.name to MR.images.favourite_icon
                                          TabComponent.Tab.Settings -> specificTab.name to MR.images.setting_icon_linear
                                      }

                                      val bottomTabsOffset = when (specificTab) {
                                          TabComponent.Tab.Account, TabComponent.Tab.Post -> 4.dp
                                          TabComponent.Tab.Saved, TabComponent.Tab.Settings -> 3.dp
                                          else -> 4.5.dp
                                      }

                                      NavigationBarItem(
                                          icon = {
                                              Icon(
                                                  painter = painterResource(icon),
                                                  contentDescription = null,
                                                  modifier = Modifier.fillMaxSize(if (specificTab == TabComponent.Tab.Saved) 0.45F else 0.50F)
                                                      .offset(y = bottomTabsOffset),
                                                  tint = Color.Unspecified,
                                              )
                                          },
                                          onClick = {
                                              child.component.onTabSelected(specificTab)
                                          },
                                          selected = false,
                                          label = {
                                              Text(
                                                  text = "${title}",
                                                  fontSize = 12.5.sp,
                                              )
                                          }
                                      )

                                  }

                              }
                          }
                      },
                      sheetDragHandle = {},
                      sheetSwipeEnabled = false,
                      sheetContent = {
                          CupertinoBottomSheetContent {
                              CupertinoButton(
                                  modifier = Modifier.padding(top = 50.dp),
                                  onClick = {
                                      scope.launch {
                                          bottomSheetState.hide()
                                      }

                                  }
                              ) {
                                  Text(
                                      text = "Hello world",
                                  )
                              }
                          }

                      },
                  ) {
                      val tabStack by child.component.stack.subscribeAsState()
                      println("CURRENT TAB STACK == ${tabStack}")
                      Children(
                          stack = tabStack,
                          modifier = Modifier,
                      ) {
                          TabView(child.component)
                      }
                  }
              }
              is HomeScreenComponent.Child.PostScreen ->  {
                  PostDetailScreen(child.component)
              }
              is HomeScreenComponent.Child.SelectLocationScreen -> SelectLocationScreen(child.component)
         }
       }

     }





