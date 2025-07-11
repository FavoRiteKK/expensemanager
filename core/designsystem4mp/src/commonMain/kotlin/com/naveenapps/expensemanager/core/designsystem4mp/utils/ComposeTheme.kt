package com.naveenapps.expensemanager.core.designsystem4mp.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.naveenapps.expensemanager.core.data4mp.utils.LWAppCompatDelegate

/**
 * Returns `true` if dark theme should be used, as a function of the [theme] and the
 * current system context.
 */
@Composable
fun shouldUseDarkTheme(theme: Int): Boolean = when (theme) {
    LWAppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> isSystemInDarkTheme()
    LWAppCompatDelegate.MODE_NIGHT_NO -> false
    LWAppCompatDelegate.MODE_NIGHT_YES -> true
    else -> {
        isSystemInDarkTheme()
    }
}
