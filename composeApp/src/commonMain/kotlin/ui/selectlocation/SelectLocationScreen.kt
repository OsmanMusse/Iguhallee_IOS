package ui.selectlocation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.value.getValue
import com.ramaas.iguhallee.MR
import decompose.location.SelectLocationComponent
import dev.icerock.moko.resources.compose.painterResource
import io.github.alexzhirkevich.cupertino.CupertinoDivider
import io.github.alexzhirkevich.cupertino.CupertinoHorizontalDivider
import io.github.alexzhirkevich.cupertino.CupertinoScaffold
import io.github.alexzhirkevich.cupertino.CupertinoText
import screens.selectlocation.components.CustomTopAppBar


@Composable
fun SelectLocationScreen(component: SelectLocationComponent) {

    val mainBackgroundColor = Color(241, 242, 243)
    val mainColor =  Color(71, 134, 255)

    val currentLocation by component.currentlySelectedLocation

    CupertinoScaffold(
        topBar = { CustomTopAppBar(component) },
        containerColor = mainBackgroundColor
    ){
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ){
                val listItem = arrayListOf("Garowe","Mogadishu","Hargeisa","Burco","Bosaso","Gaalkacyo")
                listItem.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER,{ it }))
                items(listItem){ location ->
                    Box(
                        modifier = Modifier
                            .height(60.dp)
                            .padding(start = 10.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = rememberRipple(),
                                onClick = {
                                    println("Location Selected == $location")
                                    component.onBackClick(location)
                                }
                            )
                    ){
                        CupertinoText(
                            modifier = Modifier.align(Alignment.CenterStart),
                            text = location,
                            color = if (location == currentLocation) mainColor else Color.Black,
                            fontWeight = FontWeight.Normal,
                            fontSize = 17.sp
                        )

                        if (location == currentLocation) {
                            Icon(
                                modifier = Modifier
                                    .padding(end = 26.dp)
                                    .size(19.dp)
                                    .align(Alignment.CenterEnd),
                                painter = painterResource(MR.images.checkmark_icon),
                                contentDescription = null,
                                tint = mainColor
                            )
                        }

                        CupertinoHorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                        )
                    }

                }
            }
    }
}