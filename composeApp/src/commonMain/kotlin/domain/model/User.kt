package domain.model

import kotlinx.serialization.Serializable

enum class UserType(type: String){
    Private("Private"),
    Agency("Agency")
}


@Serializable
data class User(
    val id: String,
    val postCount: Int,
    val profileName: String,
    val profileEmail: String,
    val memberSince: String,
    val usertype: UserType
)
