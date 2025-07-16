package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.repository.VersionCheckerRepository

class VersionCheckerRepositoryImpl : VersionCheckerRepository {
    override fun isAndroidQAndAbove(): Boolean = true
}
