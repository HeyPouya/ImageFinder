package ir.heydarii.imagefinder.features.search

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ir.heydarii.imagefinder.R
import ir.heydarii.imagefinder.base.BaseActivity
import ir.heydarii.imagefinder.base.BaseApplication

class SearchImageActivity : BaseActivity() {

    lateinit var viewModel: SearchImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_search)

        viewModel = ViewModelProvider(this, ((application) as BaseApplication).provider).get(SearchImageViewModel::class.java)
    }
}
