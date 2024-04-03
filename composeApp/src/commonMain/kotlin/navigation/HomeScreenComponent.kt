package navigation

import com.arkivanov.decompose.ComponentContext

class HomeScreenComponent(
       componentContext: ComponentContext,
    private val onNavigateToScreenB: (String) -> Unit
     ):ComponentContext by componentContext {

         fun onEvent(event: HomeScreenEvent){
             when(event){
                 is HomeScreenEvent.navigationToPost -> onNavigateToScreenB("From HomeScreen")
             }
         }
}