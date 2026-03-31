package com.thematch.app.ui.screen.pending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thematch.shared.domain.repository.ChallengeRepository
import com.thematch.shared.model.Challenge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PendingUiState(
    val challenge: Challenge? = null,
    val isProcessing: Boolean = false,
    val isDone: Boolean = false,
    val error: String? = null
)

class PendingViewModel(private val challengeRepository: ChallengeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PendingUiState())
    val uiState: StateFlow<PendingUiState> = _uiState.asStateFlow()

    fun setChallenge(challenge: Challenge) {
        _uiState.update { it.copy(challenge = challenge) }
    }

    fun acceptChallenge(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isProcessing = true, error = null) }
            challengeRepository.acceptChallenge(id)
                .onSuccess { _uiState.update { it.copy(isProcessing = false, isDone = true) } }
                .onFailure { e -> _uiState.update { it.copy(isProcessing = false, error = e.message) } }
        }
    }

    fun declineChallenge(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isProcessing = true, error = null) }
            challengeRepository.declineChallenge(id)
                .onSuccess { _uiState.update { it.copy(isProcessing = false, isDone = true) } }
                .onFailure { e -> _uiState.update { it.copy(isProcessing = false, error = e.message) } }
        }
    }
}
