package com.thematch.app.ui.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.thematch.app.ui.components.BottomNavBar
import com.thematch.app.ui.components.BottomNavTab
import com.thematch.app.ui.screen.challenge.ChallengeListScreen
import com.thematch.app.ui.screen.challenge.ChallengeViewModel
import com.thematch.app.ui.screen.place.PlaceListScreen
import com.thematch.app.ui.screen.place.PlaceViewModel
import com.thematch.app.ui.screen.profile.ProfileScreen
import com.thematch.app.ui.screen.profile.ProfileViewModel

@Composable
fun HomeScreen(
    profileViewModel: ProfileViewModel,
    challengeViewModel: ChallengeViewModel,
    placeViewModel: PlaceViewModel,
    onNavigateToTeamDetail: (Int) -> Unit,
    onNavigateToTeamList: () -> Unit,
    onNavigateToPending: (Int) -> Unit
) {
    var selectedTab by remember { mutableStateOf(BottomNavTab.PROFILE) }

    Scaffold(
        bottomBar = {
            BottomNavBar(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
        }
    ) { padding ->
        when (selectedTab) {
            BottomNavTab.PROFILE -> ProfileScreen(
                viewModel = profileViewModel,
                onNavigateToTeamList = onNavigateToTeamList,
                onNavigateToPending = onNavigateToPending,
                modifier = Modifier.padding(padding)
            )
            BottomNavTab.CHALLENGES -> ChallengeListScreen(
                viewModel = challengeViewModel,
                onTeamClick = onNavigateToTeamDetail,
                modifier = Modifier.padding(padding)
            )
            BottomNavTab.PLACES -> PlaceListScreen(
                viewModel = placeViewModel,
                modifier = Modifier.padding(padding)
            )
        }
    }
}
