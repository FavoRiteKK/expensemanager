package com.naveenapps.expensemanager.core.testing4mp

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

@ExperimentalCoroutinesApi
open class BaseCoroutineTest {

    lateinit var testCoroutineDispatcher: CoroutineDispatcher

    @BeforeTest
    open fun onCreate() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @AfterTest
    open fun onDestroy() {
        Dispatchers.resetMain()
    }
}
