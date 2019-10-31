package ir.heydarii.imagefinder.repository

import io.reactivex.Single
import ir.heydarii.imagefinder.pojos.ImageSearchResponseModel
import ir.heydarii.imagefinder.repository.network.NetworkRepository

/**
 * All Observables are gathered here to be used as the data layer
 */
class Repository(private val network: NetworkRepository) {
    fun searchImage(query: String, page: Int): Single<ImageSearchResponseModel> {
        return network.searchImage(query, page)
    }
}