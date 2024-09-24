package di

import app.cash.paging.PagingConfig
import util.Constants
import decompose.home.HomeListComponent
import decompose.root.DefaultRootComponent
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.repository.post.PostRepository
import domain.repository.post.PostRepositoryImpl
import domain.repository.user.UserRepository
import domain.repository.user.UserRespository_Impl
import decompose.detail.PostDetailComponent
import decompose.home.DefaultHomeScreenComponent
import decompose.landing.DefaultLandingComponent
import decompose.splash.DefaultSplashComponent
import decompose.tab.DefaultTabComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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
        UserRespository_Impl(remoteDB = get())
    }

    single {
        DefaultRootComponent(
            componentContext = get(),
            appPreferencesRepo = get(),
            homeScreenFactory = get(),
            splashScreenFactory = get(),
            landingScreenFactory = get(),
        )
    }

    single {
        DefaultSplashComponent.Factory(
            mainContext = Dispatchers.Main,
            ioContext = Dispatchers.IO,
            appPreferencesRepository = get()
        )
    }

    single {
        DefaultLandingComponent.Factory(
            mainContext = Dispatchers.Main,
            ioContext = Dispatchers.IO,
            appPreferencesRepository = get()
        )
    }


    single {
        DefaultTabComponent.Factory(homeListFactory = get())
    }


    single {
        DefaultHomeScreenComponent.Factory(
            tabFactory = get(),
            postDetailFactory = get()
        )
    }


    single {
        HomeListComponent.Factory(repo = get())
    }

    single {
        PostDetailComponent.Factory(
            postRepo = get(),
            userRepo = get()
        )
    }
}