package com.naveenapps.expensemanager.core.data.di

import com.naveenapps.expensemanager.core.repository.PlatformRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.awt.Desktop
import java.net.URI

actual fun platformRepository(): Module = module {
    singleOf(::PlatformRepositoryImpl) bind PlatformRepository::class
    single<LWPermissionsController> {
        LWPermissionsControllerImpl()
    }
}

internal class PlatformRepositoryImpl : PlatformRepository {
    override fun openWebPage(url: String) {
        val desktop = Desktop.getDesktop()
        if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
            desktop.browse(URI.create(url))
        } else {
            println("Desktop is not supported or cannot browse to the URL: $url")
        }
    }

    override fun sendEmail(email: String) {
        val desktop = Desktop.getDesktop()
        if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.MAIL)) {
            val mailtoUri = URI("mailto:$email?subject=Feedback")
            desktop.mail(mailtoUri)
        } else {
            println("Desktop is not supported or cannot mail to: $email")
        }
    }

    override fun share() {
        openWebPage("https://play.google.com/store/apps/details?id=" + "com.lws.naveenapps.expensemanager")
    }

    override fun openRateUs() {
        share()
    }
}

actual interface LWPermission
actual interface LWPermissionsController {
    actual suspend fun providePermission(permission: LWPermission)
}

internal class LWPermissionsControllerImpl : LWPermissionsController {
    override suspend fun providePermission(permission: LWPermission) {
        /* noop */
    }
}

actual object LWWriteStoragePermission : LWPermission

actual class LWDeniedAlwaysException : Exception()
actual class LWDeniedException : Exception()