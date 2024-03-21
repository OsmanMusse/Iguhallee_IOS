package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ScrollableContent() {
    LazyColumn(
//        contentPadding = PaddingValues(top = contentTopPadding),
        modifier = Modifier.background(Color(240, 240, 240))
    ) {
        items(100) { index ->
            Text("I'm item $index", modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp))
        }
    }
}