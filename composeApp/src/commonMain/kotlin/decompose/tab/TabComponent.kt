package decompose.tab

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import decompose.home.HomeListComponent
import decompose.tab.account.AccountComponent

interface TabComponent {

    val stack: Value<ChildStack<*,Child>>

    fun onTabSelected(tab: TabComponent.Tab)


    sealed interface Child {
            class Home(val component: HomeListComponent): Child
            class Account(val component: AccountComponent): Child
            class Post(val component: AccountComponent): Child
            class Saved(val component: AccountComponent): Child
            class Settings(val component: AccountComponent): Child
    }

    enum class Tab {
        Home, Account, Post, Saved, Settings;

    }
}