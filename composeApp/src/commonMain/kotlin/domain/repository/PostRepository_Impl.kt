package domain.repository

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.ramaas.iguhallee.Database
import core.Constants
import data.post.toList
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.DocumentChange
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.orderBy
import dev.gitlive.firebase.firestore.where
import domain.model.Post
import domain.model.PostStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map


class PostRepositoryImpl(
    private val remoteDB: FirebaseFirestore,
    localDB: Database,
    private val config: PagingConfig
): PostRepository {

    private val queries = localDB.postQueries

    init {
        remoteDB.setSettings(persistenceEnabled = true, cacheSizeBytes = -1L)
    }


    override suspend fun testGetAllPosts() {
        val query = remoteDB.collection("Posts")
            .orderBy("datePosted",Direction.DESCENDING)
            .where { "status" equalTo PostStatus.APPROVED.text }
            .get()

        println("Acutal Query == ${query.documents}")
    }


    override suspend fun getAllPosts(): Flow<PagingData<Post>> {

        val query = remoteDB.collection("Posts")
            .orderBy("datePosted",Direction.DESCENDING)
            .where { "status" equalTo PostStatus.APPROVED.text }
            .limit(Constants.PAGE_SIZE)

        return Pager(
            config = config
        ) {
            FirestorePagingSource(queryPostByCity = query)
        }.flow
    }


    override suspend fun getAllLikedPosts(): Flow<List<Long>> {
        return queries
            .selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default).map {
                it.map { likedPost ->
                    likedPost.toList()
                }
            }
    }

    override suspend fun getPost(postID: Long): Flow<Post> {
        println("VIEWMODEL CALLED === ${postID}")
             return remoteDB
                .collection(Constants.POST_COLLECTION)
                .document("$postID")
                .snapshots()
                .map {
                    it.data<Post>()
                }
            .flowOn(Dispatchers.IO)
        }



    override suspend fun saveLikedPost(postID: Long) {
        queries.insertLikedPost(id = null,postID = postID)
    }


    override suspend fun deleteLikedPost(postID: Long) {
        queries.deleteLikedPostById(postID)
    }


}








