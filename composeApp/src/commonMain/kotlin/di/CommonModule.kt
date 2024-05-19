package di

import app.cash.paging.PagingConfig
import core.Constants
import decompose.home.HomeListComponent
import decompose.home.HomeScreenComponent
import decompose.home.TabComponent
import decompose.root.DefaultRootComponent
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.repository.FirestorePagingSource
import domain.repository.PostRepository
import domain.repository.PostRepositoryImpl

import org.koin.dsl.module




val commonModule = module {

    single { Firebase.firestore}

    single {
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
        )
    }

    single<PostRepository> {
        PostRepositoryImpl(
            db = get(),
            config = get()
        )
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