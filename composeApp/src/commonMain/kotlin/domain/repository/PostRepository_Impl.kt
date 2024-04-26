package domain.repository

import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.orderBy
import dev.gitlive.firebase.firestore.where
import domain.model.Post
import domain.model.PostStatus


class PostRepository_Impl(
    private val db: FirebaseFirestore
): PostRepository {
    override suspend fun getAllPosts(): List<Post> {
        return db
            .collection("Posts")
            .orderBy("datePosted",Direction.DESCENDING)
            .where { "status" equalTo PostStatus.APPROVED.text }
            .get().documents.map { it.data() }

    }
}