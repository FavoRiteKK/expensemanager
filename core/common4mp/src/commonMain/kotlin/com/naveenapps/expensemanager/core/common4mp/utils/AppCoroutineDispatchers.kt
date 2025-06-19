package com.naveenapps.expensemanager.core.common4mp.utils

import kotlinx.coroutines.CoroutineDispatcher

data class AppCoroutineDispatchers(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
)
