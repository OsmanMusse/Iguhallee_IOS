package decompose.location

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class DefaultSelectLocationComponent(
    private val componentContext: ComponentContext,
    private val currentLocation: String,
    private val onGoBack: (location: String?) -> Unit
): SelectLocationComponent, ComponentContext by componentContext {

    override val currentlySelectedLocation = MutableValue(currentLocation)

    override val onBackClick: (String?) -> Unit
        get() = onGoBack

}