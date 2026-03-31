package com.thematch.app.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thematch.shared.domain.repository.ChallengeRepository
import com.thematch.shared.domain.repository.TeamRepository
import com.thematch.shared.model.Challenge
import com.thematch.shared.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val myTeams: List<Team> = emptyList(),
    val acceptedChallenges: List<Challenge> = emptyList(),
    val pendingChallenges: List<Challenge> = emptyList(),
    val historyChallenges: List<Challenge> = emptyList(),
    val selectedFilter: ChallengeFilter = ChallengeFilter.ACCEPTED,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class ChallengeFilter { ACCEPTED, PENDING, HISTORY }

class ProfileViewModel(
    private val challengeRepository: ChallengeRepository,
    private val teamRepository: TeamRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            teamRepository.getMyTeams()
                .onSuccess { teams -> _uiState.update { it.copy(myTeams = teams) } }

            challengeRepository.getAcceptedChallenges()
                .onSuccess { challenges -> _uiState.update { it.copy(acceptedChallenges = challenges) } }

            challengeRepository.getPendingChallenges()
                .onSuccess { challenges -> _uiState.update { it.copy(pendingChallenges = challenges) } }

            challengeRepository.getChallengeHistory()
                .onSuccess { challenges -> _uiState.update { it.copy(historyChallenges = challenges) } }
                .onFailure { e -> _uiState.update { it.copy(error = e.message) } }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun selectFilter(filter: ChallengeFilter) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }
}
