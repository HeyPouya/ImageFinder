package ir.heydarii.imagefinder.features.search

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ir.heydarii.imagefinder.repository.Repository

class SearchImageViewModel(val repository: Repository) : ViewModel() {

    private val disposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}