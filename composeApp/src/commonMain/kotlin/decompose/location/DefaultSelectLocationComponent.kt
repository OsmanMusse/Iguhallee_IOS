package decompose.location

import com.arkivanov.decompose.ComponentContext

class DefaultSelectLocationComponent(
    private val componentContext: ComponentContext,
    private val onGoBack: (location: String?) -> Unit
): SelectLocationComponent, ComponentContext by componentContext {

    override val onBackClick: (String?) -> Unit
        get() = onGoBack

}