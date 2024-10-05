package domain.repository.post

import app.cash.paging.PagingData
import domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getAllPosts(location: String): Flow<PagingData<Post>>

    suspend fun testGetAllPosts()
    suspend fun getAllLikedPosts(): Flow<List<Long>>

    suspend fun getPost(postID: Long): Flow<Post>

    suspend fun saveLikedPost(postID: Long)

    suspend fun deleteLikedPost(postID:Long)
}