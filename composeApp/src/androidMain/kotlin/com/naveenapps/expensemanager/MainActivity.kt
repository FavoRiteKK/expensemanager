package com.naveenapps.expensemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.vinceglb.filekit.dialogs.init
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        io.github.vinceglb.filekit.FileKit.init(this)
        setContent {
            koinApp(
                onDeclare = {
                    androidContext(this@MainActivity)
                    modules(
                        module {
                            single<ComponentActivity> { this@MainActivity }
                        }
                    )
                }
            )
        }
    }
}
