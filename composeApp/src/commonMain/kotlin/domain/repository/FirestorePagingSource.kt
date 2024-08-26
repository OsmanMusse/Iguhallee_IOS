package domain.repository


import androidx.paging.PagingState
import app.cash.paging.PagingSource
import dev.gitlive.firebase.firestore.Query
import dev.gitlive.firebase.firestore.QuerySnapshot
import dev.gitlive.firebase.firestore.startAfter
import domain.model.Post

class FirestorePagingSource(
    private val queryPostByCity: Query
): PagingSource<QuerySnapshot, Post>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Post>): QuerySnapshot? = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Post> {
//        val connection = Konnection.instance
//        if (!connection.isConnected()) return LoadResult.Error(Exception(IndexOutOfBoundsException("INTERNET IS NOT CONNECTED")))
        return try {
            val currentPage = params.key ?: queryPostByCity.get()



            println("CURRENT PAGE SIZE == ${currentPage.documents.size}")
            val lastVisibleProduct = currentPage.documents[currentPage.documents.size - 1]
            val nextPage = queryPostByCity.startAfter(lastVisibleProduct).get()
            LoadResult.Page(
                data = currentPage.documents.map { it.data() },
                prevKey = null,
                nextKey = if (nextPage.documents.isNotEmpty()) nextPage else null
            )

        } catch (e: Exception){
            println("ERROR == ${e}")
            LoadResult.Error(e)
        }
    }


}