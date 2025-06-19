package com.naveenapps.expensemanager.core.common4mp.di

import com.naveenapps.expensemanager.core.common4mp.utils.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val dispatcherModule = module {
    single {
        AppCoroutineDispatchers(
            Dispatchers.Main,
            Dispatchers.IO,
            Dispatchers.Default,
        )
    }
}
