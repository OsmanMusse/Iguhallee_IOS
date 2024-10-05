package decompose.tab.account

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value


class DefaultAccountComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext, AccountComponent {

    private val _model = MutableValue(AccountComponent.Model())

    override val model: Value<AccountComponent.Model> = _model


    override fun updateText(updateText:String){
        _model.value = _model.value.copy(text = updateText)
    }


}