package com.naveenapps.expensemanager

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ExpenseManager",
//        alwaysOnTop = true,
        state = rememberWindowState(size = DpSize(640.dp, 640.dp))
    ) {
        koinApp()
    }
}