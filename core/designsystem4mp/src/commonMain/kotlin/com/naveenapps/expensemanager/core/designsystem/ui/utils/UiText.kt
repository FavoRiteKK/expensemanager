package com.naveenapps.expensemanager.core.designsystem.ui.utils

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource

sealed class UiText {
    /**
     * It can be send from server side to the app
     */
    class DynamicString(val message: String) : UiText()

    /**
     * Constructed string resource from app
     */
    class ResString(val resId: org.jetbrains.compose.resources.StringResource) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is ResString -> {
                stringResource(resource = resId)
            }

            is DynamicString -> message
        }
    }
}
