package com.sagar.android.onlynetwork.repository

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sagar.android.logutilmaster.LogUtil
import com.sagar.android.onlynetwork.core.KeyWordsAndConstants
import com.sagar.android.onlynetwork.model.News
import com.sagar.android.onlynetwork.repository.retrofit.ApiInterface
import com.sagar.android.onlynetwork.util.Event
import com.sagar.android.onlynetwork.util.PagingDirection
import com.sagar.android.onlynetwork.util.StatusCode
import com.sagar.android.onlynetwork.util.SuperRepository
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
    private var application: Application
) : SuperRepository() {

    val mutableLiveDataGetHeadlinesError: MutableLiveData<Event<String>> = MutableLiveData()

    public fun getTopHeadLines(
        pageNumber: Int,
        pageSize: Int,
        callbackInitial: PageKeyedDataSource.LoadInitialCallback<Int, News>? = null,
        callback: PageKeyedDataSource.LoadCallback<Int, News>? = null,
        pagingDirection: PagingDirection = PagingDirection.NEXT
    ) {
        apiInterface.getTrendingNews(
            "in",
            KeyWordsAndConstants.API_KEY,
            pageNumber.toString(),
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
                        val newsListType = object : TypeToken<List<News>>() {}.type
                        when (t.code()) {
                            StatusCode.OK.code -> {
                                t.body()?.let {
                                    val responseData = JSONObject(it.string())
                                    val data = Gson().fromJson<List<News>>(
                                        responseData.getJSONArray("articles").toString(),
                                        newsListType
                                    )
                                    callbackInitial?.onResult(
                                        data,
                                        null,
                                        if (data.size < pageSize) null else 2
                                    )
                                    callback?.onResult(
                                        data,
                                        when (pagingDirection) {
                                            PagingDirection.NEXT -> {
                                                if (data.size < pageSize) null else pageNumber + 1
                                            }
                                            PagingDirection.PREVIOUS -> {
                                                if (pageNumber == 1) null else pageNumber - 1
                                            }
                                        }
                                    )
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
}