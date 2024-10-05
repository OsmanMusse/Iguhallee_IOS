package domain.repository.preferences

interface AppPreferencesRepository {
    suspend fun fetchInitialPreferences(): AppPreferences
    suspend fun setDefaultLocation(location: String)
}