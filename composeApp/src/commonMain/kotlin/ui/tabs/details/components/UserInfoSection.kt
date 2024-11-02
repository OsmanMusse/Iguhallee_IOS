package screens.details.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramaas.iguhallee.MR
import data.post.PostDetailState
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun UserInfoSection(state: PostDetailState) {
    val user = state.user

    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(70.dp)
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        Spacer(modifier = Modifier.width(75.dp))
        Column(modifier = Modifier){
            Text(
                text = "${user?.profileName} (${user?.postCount} listing)",
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Member since ${user?.memberSince}",
                color = Color(192,192,192),
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(MR.images.back_icon),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(16.5.dp)
        )
    }
}