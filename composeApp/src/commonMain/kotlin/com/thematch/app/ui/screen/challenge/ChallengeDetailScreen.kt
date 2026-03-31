package com.thematch.app.ui.screen.challenge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.thematch.app.ui.components.PlayerCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeDetailScreen(
    teamId: Int,
    viewModel: ChallengeViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToPlaceSelect: (challengerTeamId: Int, acceptedTeamId: Int) -> Unit
) {
    val uiState by viewModel.detailState.collectAsState()

    LaunchedEffect(teamId) { viewModel.loadTeamDetail(teamId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.teamDetail?.name ?: "Team Detail") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
                uiState.teamDetail?.let { detail ->
                    Text(text = detail.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "${detail.point} points", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Players", style = MaterialTheme.typography.titleSmall)
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(detail.players) { player ->
                            PlayerCard(player = player)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (uiState.myTeams.isNotEmpty()) {
                        var selectedTeamId by remember { mutableStateOf(uiState.myTeams.first().id) }

                        Text("Challenge with:", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(4.dp))

                        // Simple team selector
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            uiState.myTeams.forEach { myTeam ->
                                FilterChip(
                                    selected = selectedTeamId == myTeam.id,
                                    onClick = { selectedTeamId = myTeam.id },
                                    label = { Text(myTeam.name) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { onNavigateToPlaceSelect(selectedTeamId, teamId) },
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            Text("Challenge This Team")
                        }
                    } else {
                        Text(
                            "Create a team first to send challenges",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
