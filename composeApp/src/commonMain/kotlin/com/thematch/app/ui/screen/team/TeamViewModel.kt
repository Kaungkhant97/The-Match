package com.thematch.app.ui.screen.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thematch.shared.domain.repository.TeamRepository
import com.thematch.shared.model.Player
import com.thematch.shared.model.Team
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TeamListUiState(
    val teams: List<Team> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class TeamCreateUiState(
    val availablePlayers: List<Player> = emptyList(),
    val selectedPlayerIds: Set<Int> = emptySet(),
    val teamName: String = "",
    val isCreating: Boolean = false,
    val isCreated: Boolean = false,
    val error: String? = null
)

class TeamViewModel(private val teamRepository: TeamRepository) : ViewModel() {

    private val _listState = MutableStateFlow(TeamListUiState())
    val listState: StateFlow<TeamListUiState> = _listState.asStateFlow()

    private val _createState = MutableStateFlow(TeamCreateUiState())
    val createState: StateFlow<TeamCreateUiState> = _createState.asStateFlow()

    fun loadMyTeams() {
        viewModelScope.launch {
            _listState.update { it.copy(isLoading = true, error = null) }
            teamRepository.getMyTeams()
                .onSuccess { teams -> _listState.update { it.copy(teams = teams, isLoading = false) } }
                .onFailure { e -> _listState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }

    fun loadAvailablePlayers() {
        viewModelScope.launch {
            teamRepository.getAvailablePlayers()
                .onSuccess { players -> _createState.update { it.copy(availablePlayers = players) } }
                .onFailure { e -> _createState.update { it.copy(error = e.message) } }
        }
    }

    fun setTeamName(name: String) {
        _createState.update { it.copy(teamName = name) }
    }

    fun togglePlayer(playerId: Int) {
        _createState.update { state ->
            val newSet = if (playerId in state.selectedPlayerIds) {
                state.selectedPlayerIds - playerId
            } else {
                state.selectedPlayerIds + playerId
            }
            state.copy(selectedPlayerIds = newSet)
        }
    }

    fun createTeam() {
        val state = _createState.value
        if (state.teamName.isBlank()) return

        viewModelScope.launch {
            _createState.update { it.copy(isCreating = true, error = null) }
            teamRepository.createTeam(state.teamName, state.selectedPlayerIds.toList())
                .onSuccess { _createState.update { it.copy(isCreating = false, isCreated = true) } }
                .onFailure { e -> _createState.update { it.copy(isCreating = false, error = e.message) } }
        }
    }
}
