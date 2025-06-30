package com.naveenapps.expensemanager.core.data4mp.repository

import com.naveenapps.expensemanager.core.repository4mp.VersionCheckerRepository

class VersionCheckerRepositoryImpl : VersionCheckerRepository {
    override fun isAndroidQAndAbove(): Boolean = true
}
