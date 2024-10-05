package screens.selectlocation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramaas.iguhallee.MR
import decompose.location.SelectLocationComponent
import dev.icerock.moko.resources.compose.painterResource
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoIconButton
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBarDefaults
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun CustomTopAppBar(component: SelectLocationComponent) {

    val mainColor =  Color(71, 134, 255)

    CupertinoTopAppBar(
        title = {
            CupertinoText(
                text = "Select Location",
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp
            )
        },
        navigationIcon = {
            CupertinoIconButton(
                onClick = { component.onBackClick(null) }
            ){
                CupertinoIcon(
                    modifier = Modifier.padding(10.dp).rotate(180F),
                    painter = painterResource(MR.images.back_icon),
                    contentDescription = null,
                    tint = Color.White,
                )
            }

        },
        colors = CupertinoTopAppBarDefaults.topAppBarColors(containerColor = mainColor),
        isTranslucent = false
    )
}