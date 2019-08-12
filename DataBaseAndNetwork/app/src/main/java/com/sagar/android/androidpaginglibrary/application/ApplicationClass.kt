package com.sagar.android.androidpaginglibrary.application

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.sagar.android.androidpaginglibrary.core.KeyWordsAndConstants
import com.sagar.android.androidpaginglibrary.di.NetworkModule
import com.sagar.android.androidpaginglibrary.repository.Repository
import com.sagar.android.androidpaginglibrary.repository.room.RoomDataBase
import com.sagar.android.androidpaginglibrary.ui.mainactivity.MainActivityViewModelProvider
import com.sagar.android.logutilmaster.LogUtil
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.BuildConfig
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ApplicationClass : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {

        import(androidXModule(this@ApplicationClass))

        bind() from singleton {
            LogUtil(
                LogUtil.Builder()
                    .setCustomLogTag(KeyWordsAndConstants.LOG_TAG)
                    .setShouldHideLogInReleaseMode(false, BuildConfig.DEBUG)
            )
        }

        bind() from singleton { NetworkModule(instance()).apiInterface }

        bind() from singleton {
            Room.databaseBuilder(
                instance(),
                RoomDataBase::class.java,
                "news_Database.db"
            ).build()
        }

        bind() from singleton {
            val pref: SharedPreferences by this.kodein.instance(arg = KeyWordsAndConstants.SHARED_PREF_DB)
            Repository(
                instance(),
                pref,
                instance(),
                instance()
            )
        }

        bind() from provider { MainActivityViewModelProvider(instance(), instance()) }
    }
}