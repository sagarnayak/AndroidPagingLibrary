package com.sagar.android.androidpaginglibrary.repository

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sagar.android.androidpaginglibrary.core.KeyWordsAndConstants
import com.sagar.android.androidpaginglibrary.repository.retrofit.ApiInterface
import com.sagar.android.androidpaginglibrary.repository.room.NewsEntity
import com.sagar.android.androidpaginglibrary.repository.room.RoomDataBase
import com.sagar.android.androidpaginglibrary.util.Event
import com.sagar.android.androidpaginglibrary.util.PagingDirection
import com.sagar.android.androidpaginglibrary.util.StatusCode
import com.sagar.android.androidpaginglibrary.util.SuperRepository
import com.sagar.android.logutilmaster.LogUtil
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

class Repository(
    private var apiInterface: ApiInterface,
    private var preference: SharedPreferences,
    private var logUtil: LogUtil,
    private var application: Application,
    private var roomDataBase: RoomDataBase
) : SuperRepository() {

    val mutableLiveDataGetHeadlinesError: MutableLiveData<Event<String>> = MutableLiveData()

    public fun getTopHeadLines(
        pagingDirection: PagingDirection = PagingDirection.NEXT,
        isFirstPage: Boolean = false
    ) {
        if (isFirstPage)
            initialiseNewsPageNumber()

        if (!isAnyMoreNewsAvailable())
            return

        apiInterface.getTrendingNews(
            "in",
            KeyWordsAndConstants.API_KEY,
            getNewsPageToGet().toString(),
            KeyWordsAndConstants.PAGE_SIZE.toString()
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
                        val newsListType = object : TypeToken<List<NewsEntity>>() {}.type
                        when (t.code()) {
                            StatusCode.OK.code -> {
                                t.body()?.let {
                                    val responseData = JSONObject(it.string())
                                    val data = Gson().fromJson<List<NewsEntity>>(
                                        responseData.getJSONArray("articles").toString(),
                                        newsListType
                                    )

                                    Thread {
                                        for (news in data) {
                                            roomDataBase.getNewsDao().addNews(news)
                                        }
                                    }.start()

                                    setNextPageForNews()

                                    if (data.size < KeyWordsAndConstants.PAGE_SIZE)
                                        noMoreNewsAvailable()

                                }.run {
                                    mutableLiveDataGetHeadlinesError.postValue(
                                        Event(
                                            if (t.errorBody() != null) getErrorMessage(t.errorBody()!!) else ""
                                        )
                                    )
                                }
                            }
                            else -> {
                                mutableLiveDataGetHeadlinesError.postValue(
                                    Event(
                                        if (t.errorBody() != null) getErrorMessage(t.errorBody()!!) else ""
                                    )
                                )
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        mutableLiveDataGetHeadlinesError.postValue(
                            Event(
                                getErrorMessage(e)
                            )
                        )
                    }
                }
            )
    }

    fun deleteAllData() {
        Thread {
            roomDataBase.getNewsDao().deleteAll()
        }.start()
        initialiseNewsPageNumber()
    }

    private fun initialiseNewsPageNumber() {
        preference.edit().putInt(
            KeyWordsAndConstants.NEWS_PAGE_TO_LOAD,
            0
        ).apply()
        preference.edit().putBoolean(
            KeyWordsAndConstants.ANY_MORE_NEWS_AVAILABLE,
            true
        ).apply()
    }

    private fun getNewsPageToGet(): Int {
        return preference.getInt(
            KeyWordsAndConstants.NEWS_PAGE_TO_LOAD,
            0
        )
    }

    private fun setNextPageForNews() {
        preference.edit().putInt(
            KeyWordsAndConstants.NEWS_PAGE_TO_LOAD,
            getNewsPageToGet() + 1
        ).apply()
    }

    private fun noMoreNewsAvailable() {
        preference.edit().putBoolean(
            KeyWordsAndConstants.ANY_MORE_NEWS_AVAILABLE,
            false
        ).apply()
    }

    private fun isAnyMoreNewsAvailable(): Boolean {
        return preference.getBoolean(
            KeyWordsAndConstants.ANY_MORE_NEWS_AVAILABLE,
            false
        )
    }
}