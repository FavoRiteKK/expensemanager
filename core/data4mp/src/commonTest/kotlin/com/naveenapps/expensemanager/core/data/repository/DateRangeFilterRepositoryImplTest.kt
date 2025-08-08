package com.naveenapps.expensemanager.core.data.repository

import app.cash.turbine.test
import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.common.utils.asCurrentDateTime
import com.naveenapps.expensemanager.core.data.deleteDataStoreFile
import com.naveenapps.expensemanager.core.data.testDataStoreModule
import com.naveenapps.expensemanager.core.datastore.di.dataStore
import com.naveenapps.expensemanager.core.model.DateRangeType
import com.naveenapps.expensemanager.core.model.GroupType
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.DateRangeFilterRepository
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
class DateRangeFilterRepositoryImplTest : BaseCoroutineTest(), KoinTest {

    private val testCoroutineScope = TestScope(testCoroutineDispatcher)

    // my modules which override android context and scope in testDataStoreModule
    private val myModules = dataStore + //repository requires this
            testDataStoreModule(scope = testCoroutineScope) +
            module {
                single<AppCoroutineDispatchers> {   //repository requires this
                    AppCoroutineDispatchers(
                        main = testCoroutineDispatcher,
                        io = testCoroutineDispatcher,
                        computation = testCoroutineDispatcher,
                    )
                }

                singleOf(::DateRangeFilterRepositoryImpl) bind DateRangeFilterRepository::class
            }

    private val repository: DateRangeFilterRepository by inject()

    @BeforeTest
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(myModules)
        }
    }

    @AfterTest
    override fun onDestroy() {
        super.onDestroy()
        //on desktop-test, clear data manually
        deleteDataStoreFile(testCoroutineScope)
        testCoroutineDispatcher
        stopKoin()
    }

    @Test
    fun getDateRangeValueShouldReturnDefault() = runTest {
        repository.getDateRangeFilterType().test {
            val type = awaitItem()
            LWTruth_assertThat(type).isNotNull()
            LWTruth_assertThat(type).isEqualTo(DateRangeType.THIS_MONTH)
        }
    }

    @Test
    fun getDateRangeValueShouldUpdateOnEachUpdate() = runTest {
        repository.getDateRangeFilterType().test {
            val type = awaitItem()
            LWTruth_assertThat(type).isNotNull()
            LWTruth_assertThat(type).isEqualTo(DateRangeType.THIS_MONTH)

            repository.setDateRangeFilterType(DateRangeType.ALL)

            val updatedType = awaitItem()
            LWTruth_assertThat(updatedType).isNotNull()
            LWTruth_assertThat(updatedType).isEqualTo(DateRangeType.ALL)
        }
    }

    @Test
    fun getAllDateRangesShouldAllTypesAndNames() = runTest {
        val response = repository.getAllDateRanges()
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        val data = (response as Resource.Success).data
        LWTruth_assertThat(data).isNotEmpty()
        LWTruth_assertThat(data).hasSize(DateRangeType.entries.size)
        val firstItem = data.first()
        LWTruth_assertThat(firstItem).isNotNull()
        LWTruth_assertThat(firstItem.type).isEqualTo(DateRangeType.entries[0])
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun getDateRangeTimeFrameShouldReturnTimeFrame() = runTest {
        repository.getDateRangeTimeFrame().test {
            val dateRanges = awaitItem()
            LWTruth_assertThat(dateRanges).isNull()

            val startTime = Clock.System.now()
            val endTime = Clock.System.now()
            val dateRangesValues =
                listOf(startTime.asCurrentDateTime(), endTime.asCurrentDateTime())
            repository.setDateRanges(dateRangesValues)

            val updatedRanges = awaitItem()
            LWTruth_assertThat(updatedRanges).isNotEmpty()
            LWTruth_assertThat(updatedRanges).hasSize(2)
            LWTruth_assertThat(updatedRanges?.get(0)).isEqualTo(startTime.toEpochMilliseconds())
            LWTruth_assertThat(updatedRanges?.get(1)).isEqualTo(endTime.toEpochMilliseconds())
        }
    }

    @Test
    fun getDateRangeGroupShouldReturnTheType() = runTest {
        val todayType = repository.getTransactionGroupType(DateRangeType.TODAY)
        LWTruth_assertThat(todayType).isEqualTo(GroupType.DATE)

        val thisWeekType = repository.getTransactionGroupType(DateRangeType.THIS_WEEK)
        LWTruth_assertThat(thisWeekType).isEqualTo(GroupType.DATE)

        val thisMonthType = repository.getTransactionGroupType(DateRangeType.THIS_MONTH)
        LWTruth_assertThat(thisMonthType).isEqualTo(GroupType.DATE)

        val thisYearType = repository.getTransactionGroupType(DateRangeType.THIS_YEAR)
        LWTruth_assertThat(thisYearType).isEqualTo(GroupType.MONTH)

        val customType = repository.getTransactionGroupType(DateRangeType.CUSTOM)
        LWTruth_assertThat(customType).isEqualTo(GroupType.DATE)

        val allType = repository.getTransactionGroupType(DateRangeType.CUSTOM)
        LWTruth_assertThat(allType).isEqualTo(GroupType.DATE)
    }
}
