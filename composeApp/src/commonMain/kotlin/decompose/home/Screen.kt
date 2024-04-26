package decompose.home

import screens.AccountScreen.AccountComponent

sealed interface Screen {
    data class Home(val component: HomeScreenComponent): Screen
    data class Account(val component: AccountComponent): Screen
    data class Post(val component: HomeScreenComponent): Screen
    data class Saved(val component: HomeScreenComponent): Screen
    data class Settings(val component: HomeScreenComponent): Screen
}