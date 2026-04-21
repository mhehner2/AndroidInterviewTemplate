package com.radio.jams

import android.app.Application
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import app.cash.sqldelight.db.SqlDriver
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

@ExperimentalSerializationApi
class JamsApplication : Application() {

    private val apiModule = module {
        single {
            Json {
                ignoreUnknownKeys = true
            }
        }

        single {
            OkHttpClient.Builder().build()
        }

        single {
            GithubApi(get(), get())
        }

        single {
            AndroidSqliteDriver(GitDatabase.Schema, get(), GitDb.dbName)
        }

        single<SqlDriver> {
            get<AndroidSqliteDriver>()
        }

        single {
            GitDatabase(get())
        }

        single {
            GitDao(get())
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@JamsApplication)
            modules(apiModule)
        }
    }
}
