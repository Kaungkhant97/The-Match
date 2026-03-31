package com.thematch.app.ui.screen.challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thematch.shared.domain.repository.ChallengeRepository
import com.thematch.shared.domain.repository.TeamRepository
import com.thematch.shared.model.Player
import com.thematch.shared.model.Team
import com.thematch.shared.model.TeamDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChallengeListUiState(
    val teams: List<Team> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class ChallengeDetailUiState(
    val teamDetail: TeamDetail? = null,
    val myTeams: List<Team> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ChallengeViewModel(
    private val challengeRepository: ChallengeRepository,
    private val teamRepository: TeamRepository
) : ViewModel() {

    private val _listState = MutableStateFlow(ChallengeListUiState())
    val listState: StateFlow<ChallengeListUiState> = _listState.asStateFlow()

    private val _detailState = MutableStateFlow(ChallengeDetailUiState())
    val detailState: StateFlow<ChallengeDetailUiState> = _detailState.asStateFlow()

    fun loadChallengableTeams() {
        viewModelScope.launch {
            _listState.update { it.copy(isLoading = true, error = null) }
            challengeRepository.getChallengableTeams()
                .onSuccess { teams -> _listState.update { it.copy(teams = teams, isLoading = false) } }
                .onFailure { e -> _listState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }

    fun loadTeamDetail(teamId: Int) {
        viewModelScope.launch {
            _detailState.update { it.copy(isLoading = true, error = null) }

            teamRepository.getTeamDetail(teamId)
                .onSuccess { detail -> _detailState.update { it.copy(teamDetail = detail, isLoading = false) } }
                .onFailure { e -> _detailState.update { it.copy(isLoading = false, error = e.message) } }

            teamRepository.getMyTeams()
                .onSuccess { teams -> _detailState.update { it.copy(myTeams = teams) } }
        }
    }

    fun sendChallenge(
        challengerTeamId: Int,
        acceptedTeamId: Int,
        placeId: Int,
        reservedTime: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            challengeRepository.sendChallenge(challengerTeamId, acceptedTeamId, placeId, reservedTime)
                .onSuccess { onSuccess() }
                .onFailure { e -> _detailState.update { it.copy(error = e.message) } }
        }
    }
}
