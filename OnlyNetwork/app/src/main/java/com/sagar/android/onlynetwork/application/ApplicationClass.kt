package com.sagar.android.onlynetwork.application

import android.app.Application
import android.content.SharedPreferences
import com.sagar.android.logutilmaster.LogUtil
import com.sagar.android.onlynetwork.BuildConfig
import com.sagar.android.onlynetwork.core.KeyWordsAndConstants
import com.sagar.android.onlynetwork.di.NetworkModule
import com.sagar.android.onlynetwork.repository.Repository
import com.sagar.android.onlynetwork.ui.mainactivity.MainActivityViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
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
            val pref: SharedPreferences by this.kodein.instance(arg = KeyWordsAndConstants.SHARED_PREF_DB)
            Repository(
                instance(),
                pref,
                instance(),
                instance()
            )
        }

        bind() from provider { MainActivityViewModelProvider(instance()) }
    }
}