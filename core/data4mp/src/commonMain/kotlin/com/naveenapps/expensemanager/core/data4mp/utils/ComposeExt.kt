package com.naveenapps.expensemanager.core.data4mp.utils

import expensemanager.core.data4mp.generated.resources.Res

suspend fun convertFileToString(fileName: String): String {
    return Res.readBytes("files/$fileName").decodeToString()
}
