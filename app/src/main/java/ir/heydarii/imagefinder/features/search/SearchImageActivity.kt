package ir.heydarii.imagefinder.features.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ir.heydarii.imagefinder.R
import ir.heydarii.imagefinder.base.BaseActivity
import ir.heydarii.imagefinder.base.BaseApplication
import ir.heydarii.imagefinder.pojos.Data
import kotlinx.android.synthetic.main.activity_image_search.*

class SearchImageActivity : BaseActivity() {

    lateinit var viewModel: SearchImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_search)

        viewModel = ViewModelProvider(this, ((application) as BaseApplication).provider).get(SearchImageViewModel::class.java)

        setUpRecycler()
        viewModel.searchResponseData().observe(this, Observer {
            showImages(it.data)
            progress.visibility = View.GONE
        })

        imgSearch.setOnClickListener {
            viewModel.searchImage(edtSearch.text.toString())
            progress.visibility = View.VISIBLE
        }


    }

    private fun setUpRecycler() {
        recycler.adapter = SearchImageAdapter(emptyList())
        recycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
    }

    private fun showImages(images: List<Data>) {
        recycler.adapter = SearchImageAdapter(images)
        recycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
    }
}
