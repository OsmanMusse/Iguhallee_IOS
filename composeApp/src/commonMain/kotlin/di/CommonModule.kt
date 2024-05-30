package di

import DatabaseDriverFactory
import app.cash.paging.PagingConfig
import core.Constants
import createDatabase
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

    single { DatabaseDriverFactory().create() }

    single {
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
        )
    }

    single<PostRepository> {
        PostRepositoryImpl(
            remoteDB = get(),
            localDB = createDatabase(DatabaseDriverFactory()),
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