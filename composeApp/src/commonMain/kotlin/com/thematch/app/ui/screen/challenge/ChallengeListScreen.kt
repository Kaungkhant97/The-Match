package com.thematch.app.ui.screen.challenge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thematch.app.ui.components.TeamCard

@Composable
fun ChallengeListScreen(
    viewModel: ChallengeViewModel,
    onTeamClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.listState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadChallengableTeams() }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Find a Match", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.teams.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No teams available", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.teams) { team ->
                    TeamCard(team = team, onClick = { onTeamClick(team.id) })
                }
            }
        }

        uiState.error?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = error, color = MaterialTheme.colorScheme.error)
        }
    }
}
