package components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.painterResource
import com.ramaas.iguhallee.MR

@Composable
fun CustomTopAppBar(){
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Color(72, 134, 255)
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            backgroundColor = Color.White
        ){
            Row(modifier = Modifier.padding(horizontal = 15.dp, vertical = 14.dp)){
                Icon(
                    painter = painterResource(MR.images.search_icon),
                    contentDescription = null,
                    modifier = Modifier.fillMaxHeight(0.83f),
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = "Search Iguhallee",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
            }
        }

    }
}