package screens.MainScreen

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.model.HomeScreenState
import domain.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val repo: PostRepository
): ViewModel() {
    private val _state  = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

}