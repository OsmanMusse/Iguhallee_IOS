package domain.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import core.Constants
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.orderBy
import dev.gitlive.firebase.firestore.where
import domain.model.Post
import domain.model.PostStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow


class PostRepositoryImpl(
    private val db: FirebaseFirestore,
    private val config: PagingConfig
): PostRepository {

    init {
        db.setSettings(persistenceEnabled = false)
    }
    override suspend fun
            getAllPosts(): Flow<PagingData<Post>> {

        db.setSettings(persistenceEnabled = false)
        val query = db.collection("Posts")
            .orderBy("datePosted",Direction.DESCENDING)
            .where { "status" equalTo PostStatus.APPROVED.text }
            .limit(Constants.PAGE_SIZE)
        return Pager(
            config = config
        ) {

            FirestorePagingSource(queryPostByCity = query)
        }.flow
    }


}








