package screens.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.post.PostDetailState
import util.MakePhoneCall

@Composable
fun CallButtonSection(state: PostDetailState) {

    if (!state.isMainContentLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.White)
                .border(1.dp, Color(216,214,217,))
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 20.dp)
        ){
            Button(
                onClick = { MakePhoneCall("") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(231,84,98)
                )

            ) {
                Text(
                    text = "Call",
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp
                )
            }
        }
    }

}