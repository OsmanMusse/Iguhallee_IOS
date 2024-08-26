package di

import app.cash.paging.PagingConfig
import core.Constants
import decompose.home.HomeListComponent
import decompose.home.HomeScreenComponent
import decompose.home.TabComponent
import decompose.root.DefaultRootComponent
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.repository.PostRepository
import domain.repository.PostRepositoryImpl
import domain.repository.UserRepository
import domain.repository.UserRespository_Impl
import decompose.detail.PostDetailComponent
import org.koin.dsl.module
import util.DatabaseDriverFactory
import util.createDatabase


val commonModule = module {

    single { Firebase.firestore }

    single { DatabaseDriverFactory().create() }

    single {
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
        )
    }


    // Repositories

    single<PostRepository> {
        PostRepositoryImpl(
            remoteDB = get(),
            localDB = createDatabase(DatabaseDriverFactory()),
            config = get()
        )
    }

    single<UserRepository> {
        UserRespository_Impl(
            remoteDB = get()
        )
    }

    single {
        DefaultRootComponent(
            componentContext = get(),
            homeScreenFactory = get()
        )
    }

    single {
        TabComponent.Factory(
            homeListFactory = get()
        )
    }


    single {
        HomeScreenComponent.Factory(
            tabFactory = get(),
            postDetailFactory = get()
        )
    }


    single {
        HomeListComponent.Factory(
            repo = get()
        )
    }

    single {
        PostDetailComponent.Factory(
            postRepo = get(),
            userRepo = get()
        )
    }
}