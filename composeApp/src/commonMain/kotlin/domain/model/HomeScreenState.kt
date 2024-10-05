package domain.model

import kotlinx.serialization.Serializable

data class HomeScreenState(
    var isInitialLoad: Boolean = true,
    var shouldShowAlert: Boolean = false,
    var currentCity: String = "Garowe",
    var locationChangeReload: Boolean = false,
    var bookmarkedPosts: List<Long> = emptyList()
)
