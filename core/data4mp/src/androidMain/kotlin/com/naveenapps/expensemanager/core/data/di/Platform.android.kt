package com.naveenapps.expensemanager.core.data.di

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.net.toUri
import com.naveenapps.expensemanager.core.data4mp.R
import com.naveenapps.expensemanager.core.repository.PlatformRepository
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.storage.WriteStoragePermission
import dev.icerock.moko.permissions.PermissionsController
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun platformRepository(): Module = module {
    singleOf(::PlatformRepositoryImpl) bind PlatformRepository::class
    single<LWPermissionsController> {
        val p = PermissionsController(androidContext().applicationContext)
        p.bind(get<ComponentActivity>())
        p
    }
}

internal class PlatformRepositoryImpl(private val context: Context) : PlatformRepository {
    override fun openWebPage(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            Intent.createChooser(intent, context.getString(R.string.choose_web_browser))
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.w("PlatformRepositoryImpl", "openWebPage: ${e.message}")
        }
    }

    override fun share() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
            var shareMessage = "\nLet me recommend you this application\n\n"
            shareMessage =
                shareMessage + "https://play.google.com/store/apps/details?id=" + "com.lws.naveenapps.expensemanager" + "\n\n"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            context.startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            //e.toString();
        }
    }

    override fun openRateUs() {
        share()
    }

    override fun sendEmail(email: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, "mailto:$email".toUri())
            intent.putExtra(Intent.EXTRA_SUBJECT, "email_subject")
            intent.putExtra(Intent.EXTRA_TEXT, "email_body")
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.

        }
    }
}

actual typealias LWPermission = dev.icerock.moko.permissions.Permission
actual typealias LWPermissionsController = PermissionsController
actual typealias LWWriteStoragePermission = WriteStoragePermission
actual typealias LWDeniedAlwaysException = DeniedAlwaysException
actual typealias LWDeniedException = DeniedException