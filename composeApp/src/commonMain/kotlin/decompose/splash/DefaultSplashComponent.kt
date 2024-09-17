package decompose.splash

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import decompose.detail.PostDetailComponent
import decompose.home.HomeScreenComponent
import decompose.home.TabComponent
import domain.repository.preferences.AppPreferencesRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DefaultSplashComponent(
    componentContext: ComponentContext,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val appPreferencesRepo: AppPreferencesRepository,
    private val onNavigateTo: (locationSelected: String?) -> Unit
): SplashComponent, ComponentContext by componentContext {


    init {
        retrieveInitialAppPref()

    }


//    private val _rootState = MutableValue(SplashModel())
//    override val rootState: Value<SplashModel> = _rootState

    private fun retrieveInitialAppPref(){
        coroutineScope(mainContext).launch {
            println("DATASTORE 1 == ")
            var isDefaultLocationSelected: String?
            withContext(ioContext){
                 isDefaultLocationSelected = appPreferencesRepo.fetchInitialPreferences().defaultLocation
            }
            onNavigateTo(isDefaultLocationSelected)

        }
    }


    class Factory(
        private val mainContext: CoroutineContext,
        private val ioContext: CoroutineContext,
        private val appPreferencesRepository: AppPreferencesRepository
    ){
        fun create(
            componentContext: ComponentContext,
            onNavigateTo:(locationSelected:String?) -> Unit
        ) = DefaultSplashComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
            appPreferencesRepo = appPreferencesRepository,
            onNavigateTo = onNavigateTo
        )
    }
}