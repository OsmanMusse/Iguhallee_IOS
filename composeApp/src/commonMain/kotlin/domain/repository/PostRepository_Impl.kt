package domain.repository

import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.orderBy
import dev.gitlive.firebase.firestore.where
import domain.model.Post
import domain.model.PostStatus


class PostRepository_Impl(
    private val db: FirebaseFirestore?
): PostRepository {
    override suspend fun getAllPosts(): List<Post> {
        var image1 = "https://scontent.fgba1-1.fna.fbcdn.net/v/t39.30808-6/439708118_952248366334222_1747154082363534433_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=5f2048&_nc_ohc=Q3vHEVn2EGwAb7q1V1v&_nc_ht=scontent.fgba1-1.fna&oh=00_AfCWB9tLX-2SOBDdv7JU5waIW2ccZ97ksRvgcq7jEXW6uA&oe=6633CA27"

        val post1 = Post("First House", description = "RANDOM HOUSE 1","1250", listOf(image1))
        val post2 = Post("Second House", description = "RANDOM HOUSE 2","1250", listOf(image1))
        val post3 = Post("Third House", description = "RANDOM HOUSE 3","1250", listOf(image1))

        return listOf(post1,post2,post3)

    }
}