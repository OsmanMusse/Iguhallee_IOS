package domain.model

import kotlinx.serialization.Serializable

data class HomeScreenState(
    var isInitialLoad: Boolean = true,
    var shouldShowAlert: Boolean = false,
    var currentCity: String = "Garowe",

    var bookmarkedPosts: List<Long> = emptyList()
)
