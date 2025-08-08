package com.naveenapps.expensemanager.core.domain.usecase.settings.datarange

import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.GetDateRangeByTypeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.GetDateRangeUseCase
import com.naveenapps.expensemanager.core.model.DateRangeType
import com.naveenapps.expensemanager.core.repository.DateRangeFilterRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.kodein.mock.Mocker
import org.kodein.mock.UsesMocks
import org.kodein.mock.generated.mock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@UsesMocks(DateRangeFilterRepository::class)
class GetDateRangeUseCaseTest {

    private val mocker = Mocker()
    private val dateRangeFilterRepository: DateRangeFilterRepository by lazyOf(mocker.mock())

    private lateinit var getDateRangeUseCase: GetDateRangeUseCase

    @BeforeTest
    fun setUp() {
        getDateRangeUseCase = GetDateRangeUseCase(
            GetDateRangeByTypeUseCase(dateRangeFilterRepository),
            dateRangeFilterRepository,
        )
    }

    @AfterTest
    fun tearDown() {
        // Clean up if needed
    }

    @Test
    fun whenUserRequestTodayDateTimeRange() = runTest {
        mocker.every {
            dateRangeFilterRepository.getDateRangeFilterType()
        } returns flowOf(
            DateRangeType.TODAY
        )
        mocker.every {
            dateRangeFilterRepository.getDateRangeTimeFrame()
        } returns flowOf(listOf(0, 1))

        val result = getDateRangeUseCase.invoke()
        print(result)
    }
}
