package components.pullRefresh

enum class RefreshIndicatorState(val messageRes: String) {
    Default("Default"),
    PullingDown("PullingDown"),
    ReachedThreshold("ReachedThreshold"),
    Refreshing("Refreshing")
}