package screens


import androidx.compose.foundation.background
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
import backAnimation
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.ramaas.iguhallee.MR
import decompose.home.HomeScreenComponent
import decompose.home.TabComponent
import dev.icerock.moko.resources.compose.painterResource
import helpers.NoRippleTheme
import kotlinx.coroutines.launch
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetContent
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffold
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoNavigationBar
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoSheetState
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.decompose.NativeChildren
import com.arkivanov.essenty.backhandler.BackHandler
import kotlinx.coroutines.CoroutineScope




 @OptIn(ExperimentalCupertinoApi::class, ExperimentalDecomposeApi::class)
 @Composable
 fun BottomNavigationView(
    component: HomeScreenComponent,
    scope: CoroutineScope,
    bottomSheetState: CupertinoSheetState,
 ) {

     Children(
         stack = component.fullScreenStack,
         modifier = Modifier.fillMaxSize(),
         animation = backAnimation(
             backHandler = component.backHandler,
             onBack = {
                 println("DO SOMETHING")
             }
         ),
     ) {
         when(it.instance) {
              is HomeScreenComponent.FullScreenChild.None -> {
                  CupertinoBottomSheetScaffold(
                      modifier = Modifier.fillMaxSize(),
                      scaffoldState = CupertinoBottomSheetScaffoldState(bottomSheetState),
                      bottomBar = {
                          val grayTitleColor = Color(158, 158, 158)
                          val grayIconColor = Color(190, 190, 190)
                          val primaryColor = Color(72, 134, 255)

                          val tabStack by component.tabStack.subscribeAsState()

                          CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                              CupertinoNavigationBar(isTranslucent = false) {
                                  TabComponent.Tab.values().forEach { specificTab ->
                                      val (title, icon) = when (specificTab) {
                                          TabComponent.Tab.Home -> "Home" to MR.images.home_icon_lineal
                                          TabComponent.Tab.Account -> "Account" to MR.images.account_icon
                                          TabComponent.Tab.Post -> "Post" to MR.images.camera_icon_linear
                                          TabComponent.Tab.Saved -> "Saved" to MR.images.favourite_icon
                                          TabComponent.Tab.Settings -> "Settings" to MR.images.setting_icon_linear
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
                                              component.onTabSelected(tab = specificTab)
                                          },
                                          selected = false,
                                          label = {
                                              Text(
                                                  text = "${title}",
                                                  fontSize = 12.5.sp,
                                                  color = if (tabStack.active.instance.tab == specificTab) primaryColor else grayTitleColor
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
                      Children(
                          stack = component.tabStack,
                          modifier = Modifier,
                      ) { child ->
                          TabView(child.instance)
                      }
                  }
              }
              is HomeScreenComponent.FullScreenChild.PostScreen ->
                  CupertinoScaffold(containerColor = Color.Blue,modifier = Modifier.fillMaxSize()){
                      CupertinoButton(
                          modifier = Modifier.offset(y = 50.dp).background(Color.Blue),
                          onClick = {
                              println("BACK BUTTON CLICKED ===")
                              component.onBack()
                          }
                      ){
                          Text("Go Back",color = Color.White)
                      }
                  }

              }
         }

     }





