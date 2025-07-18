package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.data.utils.LWAppCompatDelegate
import com.naveenapps.expensemanager.core.datastore.ThemeDataStore
import com.naveenapps.expensemanager.core.model.Theme
import com.naveenapps.expensemanager.core.repository.ThemeRepository
import com.naveenapps.expensemanager.core.repository.VersionCheckerRepository
import expensemanager.core.data4mp.generated.resources.Res
import expensemanager.core.data4mp.generated.resources.dark
import expensemanager.core.data4mp.generated.resources.light
import expensemanager.core.data4mp.generated.resources.set_by_battery_saver
import expensemanager.core.data4mp.generated.resources.system_default
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val defaultTheme = Theme(
    LWAppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
    Res.string.system_default,
)

internal class ThemeRepositoryImpl(
    private val dataStore: ThemeDataStore,
    private val versionCheckerRepository: VersionCheckerRepository,
    private val dispatchers: AppCoroutineDispatchers,
) : ThemeRepository {

    private fun getDefaultTheme(): Theme {
        return defaultTheme
    }

    override suspend fun saveTheme(theme: Theme): Boolean = withContext(dispatchers.main) {
        val mode = theme.mode
        LWAppCompatDelegate.setDefaultNightMode(mode)
        withContext(dispatchers.io) {
            dataStore.setTheme(mode)
        }
        true
    }

    override suspend fun applyTheme() = withContext(dispatchers.io) {
        val theme = Theme(LWAppCompatDelegate.MODE_NIGHT_YES, Res.string.dark)
        withContext(dispatchers.main) {
            val mode = theme.mode
            LWAppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    override fun getSelectedTheme(): Flow<Theme> {
        val defaultTheme = getDefaultTheme()
        val defaultMode = defaultTheme.mode
        val themes = getThemes()
        return dataStore.getTheme(defaultMode).map { mode ->
            themes.find { theme -> theme.mode == mode } ?: defaultTheme
        }
    }

    override fun getThemes(): List<Theme> {
        return when {
            versionCheckerRepository.isAndroidQAndAbove() -> listOf(
                Theme(LWAppCompatDelegate.MODE_NIGHT_NO, Res.string.light),
                Theme(LWAppCompatDelegate.MODE_NIGHT_YES, Res.string.dark),
                Theme(LWAppCompatDelegate.MODE_NIGHT_AUTO_BATTERY, Res.string.set_by_battery_saver),
                Theme(LWAppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, Res.string.system_default),
            )

            else -> listOf(
                Theme(LWAppCompatDelegate.MODE_NIGHT_NO, Res.string.light),
                Theme(LWAppCompatDelegate.MODE_NIGHT_YES, Res.string.dark),
                Theme(LWAppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, Res.string.system_default),
            )
        }
    }
}
