package components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramaas.iguhallee.MR
import decompose.home.HomeScreenComponent
import decompose.tab.TabComponent
import dev.icerock.moko.resources.compose.painterResource
import io.github.alexzhirkevich.cupertino.CupertinoNavigationBar
import io.github.alexzhirkevich.cupertino.CupertinoSheetState
import kotlinx.coroutines.launch
import util.NoRippleTheme

@Composable
fun CupertinoBottomNavigationBar(
    bottomSheetState: CupertinoSheetState,
    child: HomeScreenComponent.Child.TabChild
) {

    val scope = rememberCoroutineScope()

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        CupertinoNavigationBar(isTranslucent = false) {
            TabComponent.Tab.values().forEach { specificTab ->
                val (title, icon) = when (specificTab) {
                    TabComponent.Tab.Home -> specificTab.name to MR.images.home_icon_lineal
                    TabComponent.Tab.Account -> specificTab.name to MR.images.account_icon1
                    TabComponent.Tab.Sell -> specificTab.name to MR.images.camera_icon_linear
                    TabComponent.Tab.Saved -> specificTab.name to MR.images.favourite_icon
                    TabComponent.Tab.Settings -> specificTab.name to MR.images.setting_icon_linear
                }

                val bottomTabsOffset = when (specificTab) {
                    TabComponent.Tab.Account, TabComponent.Tab.Sell -> 4.dp
                    TabComponent.Tab.Saved, TabComponent.Tab.Settings -> 3.dp
                    else -> 4.5.dp
                }

                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(if (specificTab == TabComponent.Tab.Saved) 0.45F else 0.50F)
                                .offset(y = bottomTabsOffset),
                            tint = Color.Unspecified,
                        )
                    },
                    onClick = {
                        if(specificTab == TabComponent.Tab.Sell) scope.launch { bottomSheetState.expand() }
                        else child.component.onTabSelected(specificTab)
                    },
                    selected = false,
                    label = {
                        Text(
                            text = title,
                            color = Color(153,153,153),
                            fontSize = 12.sp,
                        )
                    }
                )

            }

        }
    }
}