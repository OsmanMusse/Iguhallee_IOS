package domain.repository.user

import util.Constants
import dev.gitlive.firebase.firestore.FirebaseFirestore
import domain.model.User

class UserRespository_Impl(
    private val remoteDB: FirebaseFirestore
): UserRepository {

    init {
        remoteDB.setSettings(persistenceEnabled = false)
    }


    override suspend fun getUser(userID: String): User {
        return remoteDB
            .collection(Constants.USER_COLLECTION)
            .document("$userID")
            .get()
            .data()

    }


}