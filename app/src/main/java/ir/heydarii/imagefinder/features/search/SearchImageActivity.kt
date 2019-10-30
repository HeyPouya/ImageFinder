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
import kotlinx.android.synthetic.main.activity_image_search.*

class SearchImageActivity : BaseActivity() {

    private lateinit var viewModel: SearchImageViewModel
    private lateinit var adapter: SearchImageAdapter
    private val listHolder = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_search)

        viewModel = ViewModelProvider(this, ((application) as BaseApplication).provider).get(
            SearchImageViewModel::class.java
        )

        setUpRecycler()

        viewModel.searchResponseData().observe(this, Observer {
            showImages(it)
            progress.visibility = View.GONE
        })

        imgSearch.setOnClickListener {
            viewModel.searchImage(edtSearch.text.toString())
            progress.visibility = View.VISIBLE
        }


    }

    private fun setUpRecycler() {
        adapter = SearchImageAdapter(listHolder)
        recycler.adapter = adapter
        recycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
    }

    private fun showImages(images: List<String>) {
        listHolder.clear()
        listHolder.addAll(images)
        adapter.notifyDataSetChanged()
    }
}
