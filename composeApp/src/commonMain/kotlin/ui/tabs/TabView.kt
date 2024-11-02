package ui.tabs

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import decompose.tab.TabComponent
import ui.tabs.home.HomeListView
import screens.account.AccountListView
import screens.saved.SavedListView


@Composable
fun TabView(component: TabComponent){

    Children(stack = component.stack){
        println("Active TabView == ${it.instance}")
        when(val child = it.instance){
             is TabComponent.Child.Home -> HomeListView(child.component)
             is TabComponent.Child.Account -> AccountListView(child.component)
             is TabComponent.Child.Saved -> SavedListView(child.component)
             else -> Unit
        }
    }
}