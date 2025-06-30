package com.naveenapps.expensemanager.core.data4mp.repository

import app.cash.turbine.test
import com.naveenapps.expensemanager.core.common4mp.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.data4mp.deleteDataStoreFile
import com.naveenapps.expensemanager.core.data4mp.testDataStoreModule
import com.naveenapps.expensemanager.core.data4mp.utils.LWAppCompatDelegate
import com.naveenapps.expensemanager.core.datastore4mp.di.dataStoreModule
import com.naveenapps.expensemanager.core.repository4mp.ThemeRepository
import com.naveenapps.expensemanager.core.repository4mp.VersionCheckerRepository
import com.naveenapps.expensemanager.core.testing4mp.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing4mp.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.kodein.mock.Mocker
import org.kodein.mock.UsesMocks
import org.kodein.mock.generated.mock
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

@OptIn(ExperimentalCoroutinesApi::class)
class ThemeRepositoryImplTest : BaseCoroutineTest(), KoinTest {

    private lateinit var mocker: Mocker
    private lateinit var versionCheckerRepository: VersionCheckerRepository

    private val testCoroutineScope = TestScope(testCoroutineDispatcher)

    // my modules which override android context and scope in testDataStoreModule
    private val myModules = dataStoreModule + //repository requires this
            testDataStoreModule(scope = testCoroutineScope) +
            module {
                single<AppCoroutineDispatchers> {   //repository requires this
                    AppCoroutineDispatchers(
                        main = testCoroutineDispatcher,
                        io = testCoroutineDispatcher,
                        computation = testCoroutineDispatcher,
                    )
                }
                single { versionCheckerRepository }
                singleOf(::ThemeRepositoryImpl) bind ThemeRepository::class
            }
    private val repository: ThemeRepository by inject()

    @BeforeTest
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(myModules)
        }
        mocker = Mocker()
        versionCheckerRepository = mocker.mock()
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
    fun saveThemeShouldReturnTrue() = runTest {
        val response = repository.saveTheme(
            defaultTheme.copy(mode = LWAppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
        )

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isTrue()
    }

    @Test
    fun getSelectedThemeShouldReturnDefaultTheme() = runTest {
        mocker.every { versionCheckerRepository.isAndroidQAndAbove() } returns true

        repository.getSelectedTheme().test {
            val theme = awaitItem()
            LWTruth_assertThat(theme).isNotNull()
            LWTruth_assertThat(theme).isEqualTo(defaultTheme)

            val modifiedTheme =
                defaultTheme.copy(mode = LWAppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            repository.saveTheme(modifiedTheme)

            val updatedTheme = awaitItem()
            LWTruth_assertThat(updatedTheme).isNotNull()
            LWTruth_assertThat(updatedTheme.mode).isEqualTo(modifiedTheme.mode)
        }
    }

    @Test
    fun getAllThemeShouldReturnFourThemeForQAndAbove() = runTest {
        mocker.every { versionCheckerRepository.isAndroidQAndAbove() } returns true

        val themes = repository.getThemes()
        LWTruth_assertThat(themes).isNotNull()
        LWTruth_assertThat(themes).isNotEmpty()
        LWTruth_assertThat(themes).hasSize(4)
    }

    @Test
    fun getAllThemeShouldReturnThreeThemeForQBelow() = runTest {
        mocker.every { versionCheckerRepository.isAndroidQAndAbove() } returns false

        val themes = repository.getThemes()
        LWTruth_assertThat(themes).isNotNull()
        LWTruth_assertThat(themes).isNotEmpty()
        LWTruth_assertThat(themes).hasSize(3)
    }

    @Test
    fun checkApplyTheme() = runTest {
        repository.applyTheme()
    }
}
