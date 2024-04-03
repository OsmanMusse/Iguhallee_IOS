package domain.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import domain.model.Post

class PostRepository_Impl(
    private val db: FirebaseFirestore
): PostRepository {
    override suspend fun getAllPosts(): List<Post> {
      return emptyList()
    }
}