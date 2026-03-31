package com.thematch.app.ui.screen.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thematch.shared.domain.repository.PlaceRepository
import com.thematch.shared.model.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlaceListUiState(
    val places: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class PlaceViewModel(private val placeRepository: PlaceRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PlaceListUiState())
    val uiState: StateFlow<PlaceListUiState> = _uiState.asStateFlow()

    fun loadPlaces() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            placeRepository.getPlaces()
                .onSuccess { places -> _uiState.update { it.copy(places = places, isLoading = false) } }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }
}
