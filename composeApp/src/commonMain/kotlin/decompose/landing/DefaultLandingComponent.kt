package decompose.landing

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import decompose.splash.DefaultSplashComponent
import decompose.splash.SplashComponent
import domain.repository.preferences.AppPreferencesRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlin.coroutines.CoroutineContext

class DefaultLandingComponent(
    componentContext: ComponentContext,
    private val mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
    private val appPreferencesRepo: AppPreferencesRepository,
): LandingComponent, ComponentContext by componentContext {

    init {
        println("RUNNING LANDING COMPONENT === ")
    }

    override suspend fun setUserLocation(location: String){
        coroutineScope(mainContext).launch {
            withContext(ioContext){
                appPreferencesRepo.setDefaultLocation(location)
            }
        }
    }


    class Factory(
        private val mainContext: CoroutineContext,
        private val ioContext: CoroutineContext,
        private val appPreferencesRepository: AppPreferencesRepository
    ){
        fun create(
            componentContext: ComponentContext,
        ) = DefaultLandingComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
            appPreferencesRepo = appPreferencesRepository,
        )
    }

}