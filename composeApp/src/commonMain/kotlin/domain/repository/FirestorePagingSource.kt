package domain.repository


import androidx.paging.PagingState
import app.cash.paging.PagingSource
import dev.gitlive.firebase.firestore.Query
import dev.gitlive.firebase.firestore.QuerySnapshot
import dev.gitlive.firebase.firestore.startAfter
import domain.model.Post
import kotlinx.coroutines.delay

class FirestorePagingSource(
    private val queryPostByCity: Query
): PagingSource<QuerySnapshot, Post>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Post>): QuerySnapshot? = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Post> {
        return try {
            val currentPage = params.key ?: queryPostByCity.get()
            val lastVisibleProduct = currentPage.documents[currentPage.documents.size - 1]
            val nextPage = queryPostByCity.startAfter(lastVisibleProduct).get()
            LoadResult.Page(
                data = currentPage.documents.map { it.data() },
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }


}