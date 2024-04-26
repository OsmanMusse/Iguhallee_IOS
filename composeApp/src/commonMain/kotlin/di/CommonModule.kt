package di

import decompose.home.HomeListComponent
import decompose.home.HomeScreenComponent
import decompose.home.TabComponent
import decompose.root.DefaultRootComponent
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.repository.PostRepository
import domain.repository.PostRepository_Impl

import org.koin.dsl.module

val commonModule = module {

    single { Firebase.firestore}
    single<PostRepository> {
        PostRepository_Impl(get())
    }

    single {
        DefaultRootComponent(
            componentContext = get(),
            homeScreenFactory = get()
        )
    }


    single {
        HomeScreenComponent.Factory(
            tabFactory = get()
        )
    }

    single {
        HomeListComponent.Factory(
            repo = get()
        )
    }

    single {
        TabComponent.Factory(
            homeListFactory = get()
        )
    }

}