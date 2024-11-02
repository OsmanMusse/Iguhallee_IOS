package screens.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramaas.iguhallee.MR
import data.post.PostDetailState
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun DateInfoSection(state: PostDetailState){

    val post = state.post

    Spacer(modifier = Modifier.height(8.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ){
        Row(
            modifier = Modifier.padding(start = 15.dp)
        ){
            Icon(
                painter = painterResource(MR.images.clock_icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(15.dp)
            )

            Spacer(modifier = Modifier.width(7.dp))

            Text(
                text = "1 week ago",
                color = Color(192,192,192),
                fontSize = 14.5.sp
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 27.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "ID: ${post?.id?.times(-1)}",
                color = Color(192,192,192),
                fontSize = 14.5.sp
            )

            Text(
                text = "Report",
                color = Color(0,127,176),
                fontWeight = FontWeight.Normal,
                fontSize = 17.5.sp
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}