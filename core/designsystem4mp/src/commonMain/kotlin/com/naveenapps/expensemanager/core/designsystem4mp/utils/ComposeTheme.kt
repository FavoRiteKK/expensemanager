package com.naveenapps.expensemanager.core.designsystem4mp.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.naveenapps.expensemanager.core.data4mp.utils.LWAppCompatDelegate
import expensemanager.core.designsystem4mp.generated.resources.Res
import expensemanager.core.designsystem4mp.generated.resources.allDrawableResources
import expensemanager.core.designsystem4mp.generated.resources.credit_card
import expensemanager.core.designsystem4mp.generated.resources.expense
import expensemanager.core.designsystem4mp.generated.resources.ic_no_accounts
import expensemanager.core.designsystem4mp.generated.resources.income
import expensemanager.core.designsystem4mp.generated.resources.savings
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

//since generated resources are internal, we have to export accessors
sealed interface Exports {
    object Drawables : Exports {
        val credit_card = Res.drawable.credit_card
        val savings = Res.drawable.savings
        val ic_no_accounts = Res.drawable.ic_no_accounts

        fun drawableBy(key: String): DrawableResource {
            return Res.allDrawableResources[key] ?: ic_no_accounts
        }
    }

    object StringResources : Exports {
        val income = Res.string.income
        val expense = Res.string.expense
    }
}
