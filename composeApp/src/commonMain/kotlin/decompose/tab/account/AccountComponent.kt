package decompose.tab.account

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value

interface AccountComponent {
    val model: Value<Model>

    data class Model(val text: String = "Account text")

    fun updateText(updateText:String)
}


