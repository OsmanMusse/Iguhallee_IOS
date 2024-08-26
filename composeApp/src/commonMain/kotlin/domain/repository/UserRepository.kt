package domain.repository

import domain.model.User

interface UserRepository {
      suspend fun getUser(userID: String): User
}