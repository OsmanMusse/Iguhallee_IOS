package decompose.location

import com.arkivanov.decompose.value.MutableValue

interface SelectLocationComponent {
   val currentlySelectedLocation: MutableValue<String>
   val onBackClick: (location: String?) -> Unit
}