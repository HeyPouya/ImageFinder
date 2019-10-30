package ir.heydarii.imagefinder.repository.network

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.heydarii.imagefinder.pojos.ImageSearchResponseModel
import ir.heydarii.imagefinder.retrofit.RetrofitMainInterface

/**
 * All network Observables are configured here
 */
class NetworkRepository(private val mainInterface: RetrofitMainInterface) {

    fun searchImage(query: String, page: Int): Single<ImageSearchResponseModel> {
        return mainInterface.searchPhoto(query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}