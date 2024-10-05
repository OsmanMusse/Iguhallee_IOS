package components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.ramaas.iguhallee.MR
import org.jetbrains.compose.resources.ExperimentalResourceApi
import dev.icerock.moko.resources.compose.painterResource


data class BottomNavigationItem(
    val title:String,
    val icon: Painter
)
@OptIn(ExperimentalResourceApi::class)
@Composable
fun CustomBottomAppBar(){
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            icon = painterResource(MR.images.home_pic)
        ),

        BottomNavigationItem(
            title = "Account",
            icon = painterResource(MR.images.account_user)
        ),

        BottomNavigationItem(
            title = "Post",
            icon = painterResource(MR.images.camera_icon)
        ),

        BottomNavigationItem(
            title = "Saved",
            icon = painterResource(MR.images.saved_icon)
        ),

        BottomNavigationItem(
            title = "Settings",
            icon = painterResource(MR.images.settings_gear)
        )
    )
   NavigationBar(){
       var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
       items.forEachIndexed { index, items ->
           NavigationBarItem(
               selected = selectedItemIndex == index,
               onClick = {
                   selectedItemIndex = index
               },
               icon = {
                      Icon(painter = items.icon,contentDescription = null, tint = if (selectedItemIndex == index) Color(72, 134, 255) else Color.Unspecified)
               },
               label = {
                   Text(text = "${items.title}")
               },
               colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
           )
       }
   }
}