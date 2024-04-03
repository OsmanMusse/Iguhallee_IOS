package navigation

sealed interface HomeScreenEvent{
    data object navigationToPost: HomeScreenEvent
}