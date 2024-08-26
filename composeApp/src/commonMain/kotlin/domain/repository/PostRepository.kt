package domain.repository

import app.cash.paging.PagingData
import domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getAllPosts(): Flow<PagingData<Post>>

    suspend fun testGetAllPosts(): Unit
    suspend fun getAllLikedPosts(): Flow<List<Long>>

    suspend fun getPost(postID: Long): Flow<Post>

    suspend fun saveLikedPost(postID: Long)

    suspend fun deleteLikedPost(postID:Long)
}