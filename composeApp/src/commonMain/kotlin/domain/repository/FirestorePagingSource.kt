package domain.repository


import androidx.paging.PagingState
import app.cash.paging.PagingSource
import dev.gitlive.firebase.firestore.Query
import dev.gitlive.firebase.firestore.QuerySnapshot
import dev.gitlive.firebase.firestore.startAfter
import dev.tmapps.konnection.Konnection
import domain.model.Post
import screens.home.PagingError

class FirestorePagingSource(
    private val queryPostByCity: Query
): PagingSource<QuerySnapshot, Post>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Post>): QuerySnapshot? = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Post> {

        val currentPage = params.key ?: queryPostByCity.get()
        val connection = Konnection.instance
        println("CURRENT PAGE DATA == 1")
        if (!connection.isConnected()) return LoadResult.Error(IndexOutOfBoundsException(PagingError.INTERNET_CONNECTION.errorMsg))
        else if(currentPage.documents.isEmpty()) return LoadResult.Error(IndexOutOfBoundsException(PagingError.QUERY_NOT_FOUND_FROM_DATABASE.errorMsg))
        println("CURRENT PAGE DATA == 2")
        return try {

            println("CURRENT PAGE SIZE == ${currentPage.documents.size} QUERY == ${queryPostByCity}")

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