package com.naveenapps.expensemanager.core.common.di

import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val dispatcher = module {
    single {
        AppCoroutineDispatchers(
            Dispatchers.Main,
            Dispatchers.IO,
            Dispatchers.Default,
        )
    }
}
