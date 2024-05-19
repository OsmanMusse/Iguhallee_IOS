package decompose.home

sealed interface HomeScreenEvent {
    data object onRefresh: HomeScreenEvent
    data object onLoad: HomeScreenEvent
}