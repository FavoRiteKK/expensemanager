package com.naveenapps.expensemanager.core.designsystem

import de.drick.compose.hotpreview.HotPreview

// Create your own custom annotation class

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION)
@HotPreview(widthDp = 480)
annotation class AppPreviews

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION)
@HotPreview(darkMode = true, widthDp = 480)
@HotPreview(darkMode = false, widthDp = 480)
annotation class AppPreviewsLightAndDarkMode