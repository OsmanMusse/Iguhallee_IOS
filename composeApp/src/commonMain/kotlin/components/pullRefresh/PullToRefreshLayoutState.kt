package components.pullRefresh

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock

class PullToRefreshLayoutState(
    val onTimeUpdated: (Long) -> String,
) {

    private val _lastRefreshTime: MutableStateFlow<Long> = MutableStateFlow(Clock.System.now().toEpochMilliseconds())

    var refreshIndicatorState = mutableStateOf(RefreshIndicatorState.Default)
        private set

    var lastRefreshText = mutableStateOf("")
        private set

    fun updateRefreshState(refreshState: RefreshIndicatorState) {
        val now = Clock.System.now().toEpochMilliseconds()
        val timeElapsed = now - _lastRefreshTime.value
        lastRefreshText.value = onTimeUpdated(timeElapsed)
        refreshIndicatorState.value = refreshState
    }

    fun refresh() {
        _lastRefreshTime.value = Clock.System.now().toEpochMilliseconds()
        updateRefreshState(RefreshIndicatorState.Refreshing)
    }
}

@Composable
fun rememberPullToRefreshState(
    onTimeUpdated: (Long) -> String,
): PullToRefreshLayoutState =
    remember {
        PullToRefreshLayoutState(onTimeUpdated)
    }