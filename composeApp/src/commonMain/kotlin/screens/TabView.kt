package screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import decompose.home.TabComponent

@Composable
fun TabView(component: TabComponent, modifier: Modifier = Modifier){

    Children(
        stack = component.stack,
        modifier = modifier,
    ){
        println("Active TabView == ${it.instance}")
        when(val child = it.instance){
            is TabComponent.Child.Main.Home -> HomeListView(child.component)
            is TabComponent.Child.Main.Saved -> SavedListView(child.component)
            else -> Unit
        }
    }
}