package data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.gitlive.firebase.firestore.Query
import dev.gitlive.firebase.firestore.QuerySnapshot
import domain.model.Post

//@OptIn(ExperimentalPagingApi::class)
//class FirestoreRemotePagingSource(
//    private val queryPostByCity: Query
//): RemoteMediator<QuerySnapshot, Post>() {
//    @OptIn(ExperimentalPagingApi::class)
//
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<QuerySnapshot, Post>
//    ): MediatorResult {
//
//        val itemQueryResult = when(loadType){
//            LoadType.REFRESH -> 1
//            LoadType.PREPEND -> MediatorResult.Success(endOfPaginationReached = true)
//            LoadType.APPEND -> {
//                val lastItem = state.lastItemOrNull()
//                if(lastItem == null){
//                     1
//                } else {
//
//                }
//            }
//        }
//
//
//        return try {
//            val currentPage = state.key ?: queryPostByCity.get()
//            println("CURRENT PAGE SIZE == ${currentPage.documents.size}")
//            val lastVisibleProduct = currentPage.documents[currentPage.documents.size - 1]
//            val nextPage = queryPostByCity.startAfter(lastVisibleProduct).get()
//            PagingSource.LoadResult.Page(
//                data = currentPage.documents.map { it.data() },
//                prevKey = null,
//                nextKey = if (nextPage.documents.isNotEmpty()) nextPage else null
//            )
//
//        } catch (e: Exception){
//            println("ERROR == ${e}")
//            PagingSource.LoadResult.Error(e)
//        }
//    }
//}