package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val title: String,
    val description: String,
    val formattedPrice: String,
    val postImgs: List<String>,
)
