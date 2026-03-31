package com.thematch.app.di

import com.thematch.app.ui.screen.auth.AuthViewModel
import com.thematch.app.ui.screen.challenge.ChallengeViewModel
import com.thematch.app.ui.screen.pending.PendingViewModel
import com.thematch.app.ui.screen.place.PlaceViewModel
import com.thematch.app.ui.screen.profile.ProfileViewModel
import com.thematch.app.ui.screen.team.TeamViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { ChallengeViewModel(get(), get()) }
    viewModel { TeamViewModel(get()) }
    viewModel { PlaceViewModel(get()) }
    viewModel { PendingViewModel(get()) }
}
