package ir.heydarii.imagefinder.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.heydarii.imagefinder.utils.DataErrors

/**
 * All ViewModels are child of this class
 */
open class BaseViewModel : ViewModel() {

    protected val errorData = MutableLiveData<DataErrors>()

    /**
     * A function for views to subscribe on it and observe errors
     */
    fun errorObservable(): LiveData<DataErrors> = errorData
}