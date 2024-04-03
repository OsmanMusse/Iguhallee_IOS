package components.pullRefresh

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.cupertino.CupertinoActivityIndicator
import kotlin.math.roundToInt

private const val maxHeight = 100

@Composable
fun PullToRefreshIndicator(
    indicatorState: RefreshIndicatorState,
    pullToRefreshProgress: Float,
    timeElapsed: String,
) {
    val heightModifier = when (indicatorState) {
        RefreshIndicatorState.PullingDown -> {
            Modifier.height(
                (pullToRefreshProgress * 100)
                    .roundToInt()
                    .coerceAtMost(maxHeight).dp,
            )
        }
        RefreshIndicatorState.ReachedThreshold -> Modifier.height(maxHeight.dp)
        RefreshIndicatorState.Refreshing -> Modifier.wrapContentHeight()
        RefreshIndicatorState.Default -> Modifier.height(0.dp)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(241, 242, 243))
            .then(heightModifier)
            .padding(15.dp),
        contentAlignment = Alignment.BottomStart,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            println("PROGRESS == ${pullToRefreshProgress}")

            if (indicatorState == RefreshIndicatorState.Refreshing) {
                CupertinoActivityIndicator()
            }
            else if(pullToRefreshProgress > 1){
                CupertinoActivityIndicator()
            }else {
                CupertinoActivityIndicator(progress = pullToRefreshProgress)
            }
        }
    }
}