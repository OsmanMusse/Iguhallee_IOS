package domain.repository.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first



data class AppPreferences(
    val defaultLocation: String? = null,
    val isDefaultLocationSelected: Boolean = false
)

class AppPreferencesRepository_Impl(
    private val datastore: DataStore<Preferences>
): AppPreferencesRepository {

    private object PreferencesKeys {
        val DEFAULT_LOCATION = stringPreferencesKey("defaultLocation")
        val IS_DEFAULT_LOCATION_SELECTED = booleanPreferencesKey("isDefaultLocationSelected")
    }

    /**
     * Use this if you don't want to observe a flow and want a single shot data.
     */
    override suspend fun fetchInitialPreferences() =
        mapAppPreferences(datastore.data.first().toPreferences())

    override suspend fun setDefaultLocation(location: String) {
        datastore.edit { preferences ->
            preferences[PreferencesKeys.DEFAULT_LOCATION] = location
            preferences[PreferencesKeys.IS_DEFAULT_LOCATION_SELECTED] = true

        }
    }


    private fun mapAppPreferences(preferences: Preferences): AppPreferences {
        val defaultLocation = preferences[PreferencesKeys.DEFAULT_LOCATION] ?: null
        println("DEFAULT LOCATION == ${defaultLocation}")
        val isDefaultLocationSelected = defaultLocation != null
        println("IS DEFAULT LOCATION SELECTED == ${isDefaultLocationSelected}")
        return AppPreferences(defaultLocation,isDefaultLocationSelected)
    }

}