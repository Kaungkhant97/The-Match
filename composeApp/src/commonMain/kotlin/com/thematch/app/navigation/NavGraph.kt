package com.thematch.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.thematch.app.ui.screen.auth.AuthViewModel
import com.thematch.app.ui.screen.auth.LoginScreen
import com.thematch.app.ui.screen.auth.RegisterScreen
import com.thematch.app.ui.screen.challenge.ChallengeDetailScreen
import com.thematch.app.ui.screen.challenge.ChallengeViewModel
import com.thematch.app.ui.screen.home.HomeScreen
import com.thematch.app.ui.screen.pending.PendingScreen
import com.thematch.app.ui.screen.pending.PendingViewModel
import com.thematch.app.ui.screen.place.PlaceSelectScreen
import com.thematch.app.ui.screen.place.PlaceViewModel
import com.thematch.app.ui.screen.profile.ProfileViewModel
import com.thematch.app.ui.screen.team.TeamCreateScreen
import com.thematch.app.ui.screen.team.TeamListScreen
import com.thematch.app.ui.screen.team.TeamViewModel
import org.koin.compose.viewmodel.koinViewModel

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val CHALLENGE_DETAIL = "challenge/{teamId}"
    const val PLACE_SELECT = "place-select/{challengerTeamId}/{acceptedTeamId}"
    const val TEAM_LIST = "teams"
    const val TEAM_CREATE = "teams/create"
    const val PENDING = "pending/{challengeId}"
}

@Composable
fun NavGraph(navController: NavHostController, startDestination: String = Routes.LOGIN) {
    val authViewModel: AuthViewModel = koinViewModel()
    val profileViewModel: ProfileViewModel = koinViewModel()
    val challengeViewModel: ChallengeViewModel = koinViewModel()
    val teamViewModel: TeamViewModel = koinViewModel()
    val placeViewModel: PlaceViewModel = koinViewModel()
    val pendingViewModel: PendingViewModel = koinViewModel()

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.LOGIN) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) },
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                profileViewModel = profileViewModel,
                challengeViewModel = challengeViewModel,
                placeViewModel = placeViewModel,
                onNavigateToTeamDetail = { teamId ->
                    navController.navigate("challenge/$teamId")
                },
                onNavigateToTeamList = {
                    navController.navigate(Routes.TEAM_LIST)
                },
                onNavigateToPending = { challengeId ->
                    navController.navigate("pending/$challengeId")
                }
            )
        }

        composable(
            Routes.CHALLENGE_DETAIL,
            arguments = listOf(navArgument("teamId") { type = NavType.IntType })
        ) { backStackEntry ->
            val teamId = backStackEntry.arguments?.getInt("teamId") ?: return@composable
            ChallengeDetailScreen(
                teamId = teamId,
                viewModel = challengeViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPlaceSelect = { challengerTeamId, acceptedTeamId ->
                    navController.navigate("place-select/$challengerTeamId/$acceptedTeamId")
                }
            )
        }

        composable(
            Routes.PLACE_SELECT,
            arguments = listOf(
                navArgument("challengerTeamId") { type = NavType.IntType },
                navArgument("acceptedTeamId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val challengerTeamId = backStackEntry.arguments?.getInt("challengerTeamId") ?: return@composable
            val acceptedTeamId = backStackEntry.arguments?.getInt("acceptedTeamId") ?: return@composable
            PlaceSelectScreen(
                challengerTeamId = challengerTeamId,
                acceptedTeamId = acceptedTeamId,
                placeViewModel = placeViewModel,
                challengeViewModel = challengeViewModel,
                onNavigateBack = { navController.popBackStack() },
                onChalleneSent = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.TEAM_LIST) {
            TeamListScreen(
                viewModel = teamViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToRegister = { navController.navigate(Routes.TEAM_CREATE) }
            )
        }

        composable(Routes.TEAM_CREATE) {
            TeamCreateScreen(
                viewModel = teamViewModel,
                onNavigateBack = { navController.popBackStack() },
                onTeamCreated = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(
            Routes.PENDING,
            arguments = listOf(navArgument("challengeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val challengeId = backStackEntry.arguments?.getInt("challengeId") ?: return@composable
            PendingScreen(
                challengeId = challengeId,
                viewModel = pendingViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
