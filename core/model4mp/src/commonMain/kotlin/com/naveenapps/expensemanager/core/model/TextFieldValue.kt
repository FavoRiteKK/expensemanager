package com.naveenapps.expensemanager.core.model

data class TextFieldValue<T>(
    var value: T,
    var valueError: Boolean = false,
    val onValueChange: ((T) -> Unit)?,
) {
    @OptIn(ExperimentalStdlibApi::class)
    override fun toString(): String {
        return "TextFieldValue(value=$value, valueError=$valueError, onValueChange=${onValueChange.hashCode().toHexString()})"
    }
}
