package decompose.landing

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import decompose.splash.SplashComponent

interface LandingComponent {
    suspend fun setUserLocation(location:String)
}