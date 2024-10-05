package di


import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import decompose.root.RootComponent
import org.koin.core.Koin
import org.koin.dsl.module

val iosModule = module {

    single { LifecycleRegistry() }
    single<ComponentContext> {DefaultComponentContext(get<LifecycleRegistry>())}

}
fun initIOS()  = initKoin(additionalModules = listOf(iosModule))

val Koin.rootComponent: RootComponent
    get() = get()

val Koin.lifecycleRegistery: LifecycleRegistry
    get() = get()