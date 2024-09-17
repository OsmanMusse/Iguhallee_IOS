package domain.repository.user

import domain.model.User

interface UserRepository {
      suspend fun getUser(userID: String): User
}