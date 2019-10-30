package ir.heydarii.imagefinder.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.heydarii.imagefinder.features.search.SearchImageViewModel
import ir.heydarii.imagefinder.repository.Repository
import ir.heydarii.imagefinder.repository.network.NetworkRepository
import ir.heydarii.imagefinder.retrofit.RetrofitMainInterface

/**
 * We use ViewModelInjector to provide data repository for viewModels
 */
class ViewModelInjector(mainInterface: RetrofitMainInterface) :
        ViewModelProvider.Factory {

    private val repository = Repository(NetworkRepository(mainInterface))

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            when {
                modelClass.isAssignableFrom(SearchImageViewModel::class.java) -> SearchImageViewModel(
                        repository
                ) as T
                else -> throw IllegalArgumentException("Not provided ${modelClass.name} in ViewModelProvider")
            }
}
