package com.naveenapps.expensemanager.core.data.utils

import expensemanager.core.data4mp.generated.resources.Res

internal suspend fun convertFileToString(fileName: String): String {
    //this api is package scoped, eg: <module>.generated.resources
    return Res.readBytes("files/$fileName").decodeToString()
}

object LWAppCompatDelegate {
    const val MODE_NIGHT_FOLLOW_SYSTEM = -1
    const val MODE_NIGHT_NO = 1
    const val MODE_NIGHT_YES = 2
    const val MODE_NIGHT_AUTO_BATTERY = 3

    var sDefaultNightMode: Int = -100

    fun setDefaultNightMode(mode: Int) {
        if (sDefaultNightMode != mode) sDefaultNightMode = mode
    }
}
