package domain.model

import kotlinx.serialization.Serializable

data class HomeScreenState(
    val isLoading: Boolean = false,
    val postList: List<Post> = emptyList()
)
