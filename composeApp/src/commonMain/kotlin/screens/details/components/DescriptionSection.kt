package screens.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.post.PostDetailState
import screens.details.util.ExpandableText

@Composable
fun DescriptionSection(state: PostDetailState){

    val post = state.post

    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(start = 15.dp,top = 15.dp, bottom =  10.dp, end = 15.dp)
    ){
        ExpandableText(
            text = "${post?.descriptions}"

        )
    }
}