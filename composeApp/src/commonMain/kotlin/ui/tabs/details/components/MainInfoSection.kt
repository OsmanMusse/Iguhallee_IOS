package screens.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.post.PostDetailState

@Composable
fun MainInfoSection(state: PostDetailState){

    val user = state.user

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 15.dp, vertical = 23.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "Seller type",
            color = Color(192,192,192),
            fontSize = 18.sp
        )
        Text(
            text = "${user?.usertype?.name}",
            color = Color(33,33,33),
            fontSize = 18.sp
        )
    }
}