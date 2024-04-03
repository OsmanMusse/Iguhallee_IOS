package navigation

import com.arkivanov.decompose.ComponentContext

class  PostDetailScreenComponent (
    val text: String,
    private val onGoBack: () -> Unit,
    componentContext: ComponentContext
): ComponentContext by componentContext {
     fun goBack(){
         onGoBack()
     }
}