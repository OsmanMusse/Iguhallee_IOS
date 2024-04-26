package navigation

import com.arkivanov.decompose.ComponentContext



class  PostDetailComponent (
    val text: String,
    private val onGoBack: () -> Unit,
    componentContext: ComponentContext
): ComponentContext by componentContext {
     fun goBack(){
         onGoBack()
     }
}