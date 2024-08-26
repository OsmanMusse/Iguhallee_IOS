package data.post

import domain.model.Post
import domain.model.User


data class PostDetailState(
    var isMainContentLoading: Boolean = true,
    var isUserProfileLoading: Boolean = false,
    var post: Post? = null,
    var user: User? = null,
    var isPostBookmarked: Boolean = false
)