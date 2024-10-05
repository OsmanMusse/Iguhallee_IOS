package screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import decompose.tab.TabComponent
import screens.account.AccountListView

@Composable
fun TabView(component: TabComponent, modifier: Modifier = Modifier){

    Children(
        stack = component.stack,
        modifier = modifier,
    ){
        println("Active TabView == ${it.instance}")
        when(val child = it.instance){
             is TabComponent.Child.Home -> HomeListView(child.component)
             is TabComponent.Child.Account -> AccountListView(child.component)
             is TabComponent.Child.Saved -> SavedListView(child.component)
            else -> Unit
        }
    }
}