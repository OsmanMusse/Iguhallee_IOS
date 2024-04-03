package di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import domain.repository.PostRepository
import domain.repository.PostRepository_Impl
import org.koin.dsl.module
import screens.MainScreen.HomeViewModel


val appModule = module {
    factory { Firebase.firestore }

    single<PostRepository> {
        PostRepository_Impl(get())
    }
    single{
        HomeViewModel(get())
    }


}