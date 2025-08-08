package com.naveenapps.expensemanager.core.designsystem.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.naveenapps.expensemanager.core.data.utils.LWAppCompatDelegate
import expensemanager.core.designsystem4mp.generated.resources.Res
import expensemanager.core.designsystem4mp.generated.resources.allDrawableResources
import expensemanager.core.designsystem4mp.generated.resources.ic_no_accounts
import org.jetbrains.compose.resources.DrawableResource

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

object Exports {
    fun drawableBy(key: String): DrawableResource {
        return Res.allDrawableResources[key] ?: Res.drawable.ic_no_accounts
    }
}
