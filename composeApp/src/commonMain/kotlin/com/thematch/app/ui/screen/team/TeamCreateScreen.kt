package com.thematch.app.ui.screen.team

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thematch.app.ui.components.PlayerCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamCreateScreen(
    viewModel: TeamViewModel,
    onNavigateBack: () -> Unit,
    onTeamCreated: () -> Unit
) {
    val createState by viewModel.createState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadAvailablePlayers() }

    LaunchedEffect(createState.isCreated) {
        if (createState.isCreated) onTeamCreated()
    }

    // Step 1: Enter name, Step 2: Pick players
    var step by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (step == 1) "Team Name" else "Select Players") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (step == 2) step = 1 else onNavigateBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            if (step == 1) {
                // Team name entry
                Spacer(modifier = Modifier.height(32.dp))
                Text("Enter your team name", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = createState.teamName,
                    onValueChange = { viewModel.setTeamName(it) },
                    label = { Text("Team Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { step = 2 },
                    enabled = createState.teamName.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Next: Pick Players")
                }
            } else {
                // Player selection
                Text(
                    "Select players for ${createState.teamName}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "${createState.selectedPlayerIds.size} selected",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(createState.availablePlayers) { player ->
                        PlayerCard(
                            player = player,
                            isSelected = player.id in createState.selectedPlayerIds,
                            onToggle = { viewModel.togglePlayer(player.id) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.createTeam() },
                    enabled = !createState.isCreating,
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    if (createState.isCreating) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Create Team")
                    }
                }

                createState.error?.let { error ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
