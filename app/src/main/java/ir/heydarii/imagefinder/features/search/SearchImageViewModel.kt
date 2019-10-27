package ir.heydarii.imagefinder.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.imagefinder.pojos.ImageSearchResponseModel
import ir.heydarii.imagefinder.repository.Repository

class SearchImageViewModel(private val repository: Repository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val searchResponse = MutableLiveData<ImageSearchResponseModel>()

    fun searchImage(query: String, page: Int): LiveData<ImageSearchResponseModel> {
        disposable.add(
                repository.searchImage(query, page)
                        .subscribe({
                            searchResponse.value = it
                        }, {
                            Logger.d(it)
                            // TODO : Add some error handling
                        }))
        return searchResponse

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}