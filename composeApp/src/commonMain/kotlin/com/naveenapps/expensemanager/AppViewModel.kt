package com.naveenapps.expensemanager

import androidx.lifecycle.ViewModel
import com.naveenapps.expensemanager.core.domain4mp.usecase.settings.theme.GetCurrentThemeUseCase

class AppViewModel(
    getCurrentThemeUseCase: GetCurrentThemeUseCase,
    getOnboardingStatusUseCase: GetOnboardingStatusUseCase,
) : ViewModel() {
}