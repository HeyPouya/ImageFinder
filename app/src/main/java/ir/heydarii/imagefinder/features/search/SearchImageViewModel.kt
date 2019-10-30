package ir.heydarii.imagefinder.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.imagefinder.repository.Repository

class SearchImageViewModel(private val repository: Repository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val searchResponse = MutableLiveData<List<String>>()
    private val imageUrlList = arrayListOf<String>()
    private var lastSearchTerm = ""
    private var page = 1
    private var isLoading = false


    fun searchImage(query: String) {

        if (isLoading) return else isLoading = true

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

    fun searchResponseData(): LiveData<List<String>> = searchResponse

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}