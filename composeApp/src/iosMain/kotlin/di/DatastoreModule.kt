package di

import domain.repository.preferences.AppPreferencesRepository
import domain.repository.preferences.AppPreferencesRepository_Impl
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import util.DATA_STORE_FILE_NAME
import util.createDataStore

@OptIn(ExperimentalForeignApi::class)
actual fun getDatastoreModuleByPlatform() = module {
    single {
        createDataStore {
            val directory = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
            requireNotNull(directory).path + "/$DATA_STORE_FILE_NAME"

        }
    }


    single<AppPreferencesRepository> {
        AppPreferencesRepository_Impl(datastore = get())
    }
}