package ir.heydarii.imagefinder.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.imagefinder.base.BaseViewModel
import ir.heydarii.imagefinder.repository.Repository

/**
 * ViewModel for search image view
 * @repository : needs data repository as argument to work properly
 */
class SearchImageViewModel(private val repository: Repository) : BaseViewModel() {

    private val disposable = CompositeDisposable()
    private val searchResponse = MutableLiveData<List<String>>()
    private val imageUrlList = arrayListOf<String>()
    private var lastSearchTerm = ""
    private var page = 1
    private var isLoading = false


    /**
     * Asks repository to fetch images.
     * If user reaches end of the recycler view, it will fetch next page.
     * if user changes the search phrase, it will fetch the first page of that phrase.
     */
    fun searchImage(query: String) {

        //returns if another fetch is processing
        if (isLoading) return else isLoading = true

        //checks to reset page number and holder list or just increase the page number
        checkToResetOrFetchNextPage(query)

        disposable.add(
                repository.searchImage(query, page)
                        .map {

                            val urlList = arrayListOf<String>()
                            it.data.forEach { imageAddress ->
                                urlList.add(imageAddress.assets.preview.url)
                            }
                            urlList
                        }
                        .subscribe({
                            isLoading = false
                            imageUrlList.addAll(it)
                            searchResponse.value = imageUrlList
                        }, {
                            isLoading = false
                            Logger.d(it)
                            if (page > 0)
                                page--
                            // TODO : Add some error handling
                        })
        )
    }

    private fun checkToResetOrFetchNextPage(query: String) {
        if (query == lastSearchTerm)
            page++
        else
            resetValues(query)
    }

    private fun resetValues(query: String) {
        page = 1
        imageUrlList.clear()
        lastSearchTerm = query
    }

    /**
     * Provides a safe access for the searchResponse
     */
    fun searchResponseData(): LiveData<List<String>> = searchResponse

    /**
     * Disposing the disposable here
     */
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}