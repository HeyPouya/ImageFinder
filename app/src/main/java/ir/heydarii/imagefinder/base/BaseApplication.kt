package ir.heydarii.imagefinder.base

import android.app.Application
import ir.heydarii.imagefinder.base.di.DaggerRetrofitComponent

class BaseApplication : Application() {

    lateinit var provider: ViewModelInjector

    override fun onCreate() {
        super.onCreate()


        val mainInterface = DaggerRetrofitComponent.create().getMainInterface()
        provider = ViewModelInjector(mainInterface)
    }
}