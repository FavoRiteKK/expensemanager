package com.naveenapps.expensemanager.core.testing

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

expect open class BaseTest()

@ExperimentalCoroutinesApi
abstract class BaseCoroutineTest : BaseTest() {

    protected var testCoroutineDispatcher: CoroutineDispatcher = StandardTestDispatcher()

    open fun onCreate() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    open fun onDestroy() {
        Dispatchers.resetMain()
    }
}
