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

    fun searchImage(query: String) {
        disposable.add(
            repository.searchImage(query, 1)
                .map {

                    val urlList = arrayListOf<String>()
                    it.data.forEach { imageAddress ->
                        urlList.add(imageAddress.assets.preview.url)
                    }
                    urlList
                }
                .subscribe({
                    imageUrlList.addAll(it)
                    searchResponse.value = imageUrlList
                }, {
                    Logger.d(it)
                    // TODO : Add some error handling
                })
        )
    }

    fun searchResponseData(): LiveData<List<String>> = searchResponse

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}