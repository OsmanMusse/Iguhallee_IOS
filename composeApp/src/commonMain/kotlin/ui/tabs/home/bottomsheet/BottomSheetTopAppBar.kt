package ui.tabs.home.bottomsheet

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.alexzhirkevich.cupertino.CupertinoSheetState
import kotlinx.coroutines.launch

@Composable
fun CupertinoBottomSheetTopAppBar(bottomSheetState: CupertinoSheetState) {

    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier.safeContentPadding()
    ) {
        IconButton(
            onClick = {
                scope.launch { bottomSheetState.hide() }
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}