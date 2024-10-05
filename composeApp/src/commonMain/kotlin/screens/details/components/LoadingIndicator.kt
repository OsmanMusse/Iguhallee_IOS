package screens.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import io.github.alexzhirkevich.cupertino.CupertinoActivityIndicator
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun ShowLoadingIndicator(size: DpSize, padding: PaddingValues){
    Box(modifier = Modifier.fillMaxWidth()){
        CupertinoActivityIndicator(
            modifier = Modifier
                .size(size)
                .padding(padding)
                .align(Alignment.Center)
        )
    }
}