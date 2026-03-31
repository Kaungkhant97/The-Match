package com.thematch.app.ui.screen.place

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
import com.thematch.app.ui.components.PlaceCard
import com.thematch.app.ui.screen.challenge.ChallengeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceSelectScreen(
    challengerTeamId: Int,
    acceptedTeamId: Int,
    placeViewModel: PlaceViewModel,
    challengeViewModel: ChallengeViewModel,
    onNavigateBack: () -> Unit,
    onChalleneSent: () -> Unit
) {
    val placeState by placeViewModel.uiState.collectAsState()
    var selectedPlaceId by remember { mutableStateOf<Int?>(null) }
    var selectedDate by remember { mutableStateOf("2026-04-15T14:00:00") }

    LaunchedEffect(Unit) { placeViewModel.loadPlaces() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Venue") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            if (placeState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Text("Choose a venue:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(placeState.places) { place ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedPlaceId == place.id)
                                    MaterialTheme.colorScheme.secondaryContainer
                                else MaterialTheme.colorScheme.surface
                            )
                        ) {
                            PlaceCard(place = place, onClick = { selectedPlaceId = place.id })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = { selectedDate = it },
                    label = { Text("Date & Time (ISO format)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        selectedPlaceId?.let { placeId ->
                            challengeViewModel.sendChallenge(
                                challengerTeamId = challengerTeamId,
                                acceptedTeamId = acceptedTeamId,
                                placeId = placeId,
                                reservedTime = selectedDate,
                                onSuccess = onChalleneSent
                            )
                        }
                    },
                    enabled = selectedPlaceId != null && selectedDate.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Send Challenge")
                }
            }
        }
    }
}
