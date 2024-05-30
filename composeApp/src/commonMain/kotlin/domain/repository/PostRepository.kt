package domain.repository

import app.cash.paging.PagingData
import domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getAllPosts(): Flow<PagingData<Post>>
    suspend fun getAllLikedPosts(): Flow<List<Long>>

    suspend fun saveLikedPost(postID: Long)

    suspend fun deleteSavedPost(postID:Long)
}