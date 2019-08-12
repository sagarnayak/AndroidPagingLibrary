package com.sagar.android.onlynetwork.repository

import android.app.Application
import android.content.SharedPreferences
import com.sagar.android.logutilmaster.LogUtil
import com.sagar.android.onlynetwork.core.KeyWordsAndConstants
import com.sagar.android.onlynetwork.repository.retrofit.ApiInterface
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response

class Repository(
    private var apiInterface: ApiInterface,
    private var preference: SharedPreferences,
    private var logUtil: LogUtil,
    private var application: Application
) {

    public fun getTopHeadLines(pageNumber: Int) {
        apiInterface.getTrendingNews(
            "in",
            KeyWordsAndConstants.API_KEY,
            pageNumber.toString(),
            KeyWordsAndConstants.PAGE_SIZE
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : Observer<Response<ResponseBody>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: Response<ResponseBody>) {

                    }

                    override fun onError(e: Throwable) {

                    }
                }
            )
    }
}