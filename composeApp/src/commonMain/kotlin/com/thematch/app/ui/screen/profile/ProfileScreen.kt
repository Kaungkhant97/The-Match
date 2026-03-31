package com.thematch.app.ui.screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thematch.app.ui.components.ChallengeCard

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateToTeamList: () -> Unit,
    onNavigateToPending: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadProfile() }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("My Profile", style = MaterialTheme.typography.headlineSmall)
            TextButton(onClick = onNavigateToTeamList) { Text("My Teams") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filter buttons
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ChallengeFilter.entries.forEach { filter ->
                FilterChip(
                    selected = uiState.selectedFilter == filter,
                    onClick = { viewModel.selectFilter(filter) },
                    label = { Text(filter.name.lowercase().replaceFirstChar { it.uppercase() }) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val challenges = when (uiState.selectedFilter) {
                ChallengeFilter.ACCEPTED -> uiState.acceptedChallenges
                ChallengeFilter.PENDING -> uiState.pendingChallenges
                ChallengeFilter.HISTORY -> uiState.historyChallenges
            }

            if (challenges.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No ${uiState.selectedFilter.name.lowercase()} challenges", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(challenges) { challenge ->
                        ChallengeCard(
                            challenge = challenge,
                            onClick = if (challenge.status == "pending") {
                                { onNavigateToPending(challenge.id) }
                            } else null
                        )
                    }
                }
            }
        }
    }
}
