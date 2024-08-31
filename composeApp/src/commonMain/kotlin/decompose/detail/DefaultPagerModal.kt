package decompose.detail

import com.arkivanov.decompose.ComponentContext

class DefaultPagerModal(
    private val componentContext: ComponentContext,
    private val onDismissClicked: () -> Unit
): PagerModal, ComponentContext by componentContext {


    override fun onDismissModal() {
        println("MODAL DISMISS CALLED ===")
        onDismissClicked()
    }

}