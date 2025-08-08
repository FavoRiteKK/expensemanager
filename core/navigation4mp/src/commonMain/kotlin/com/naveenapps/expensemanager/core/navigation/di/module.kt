package com.naveenapps.expensemanager.core.navigation.di

import com.naveenapps.expensemanager.core.navigation.AppComposeNavigator
import com.naveenapps.expensemanager.core.navigation.ExpenseManagerComposeNavigator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigation = module {
    singleOf(::ExpenseManagerComposeNavigator) bind AppComposeNavigator::class
}