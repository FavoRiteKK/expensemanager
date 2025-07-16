package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.repository.VersionCheckerRepository
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.kodein.mock.Mocker
import org.kodein.mock.UsesMocks
import org.kodein.mock.generated.mock
import kotlin.test.Test

@UsesMocks(VersionCheckerRepository::class)
@OptIn(ExperimentalCoroutinesApi::class)
class VersionCheckerRepositoryImplTest : BaseCoroutineTest() {

    private val mocker = Mocker()
    private val versionCheckerRepository: VersionCheckerRepository = mocker.mock()

    @Test
    fun whenAccessingAndroidQAndAboveShouldReturnTheTrue() = runTest {
        mocker.every { versionCheckerRepository.isAndroidQAndAbove() } returns true
        LWTruth_assertThat(versionCheckerRepository.isAndroidQAndAbove()).isTrue()
    }

    @Test
    fun whenAccessingAndroidQAndAboveShouldReturnTheFalse() = runTest {
        mocker.every { versionCheckerRepository.isAndroidQAndAbove() } returns false
        LWTruth_assertThat(versionCheckerRepository.isAndroidQAndAbove()).isFalse()
    }
}
