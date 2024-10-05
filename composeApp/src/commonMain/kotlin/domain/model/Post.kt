package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val title: String,
    val descriptions: String,
    val formattedPrice: String,
    val district: String,
    val location: String,
    val postImgs: List<String>,
)
