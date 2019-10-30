package ir.heydarii.imagefinder.features.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ir.heydarii.imagefinder.R
import ir.heydarii.imagefinder.base.BaseActivity
import ir.heydarii.imagefinder.base.BaseApplication
import kotlinx.android.synthetic.main.activity_image_search.*

/**
 * Activity that provides view for user to search an image
 */
class SearchImageActivity : BaseActivity() {

    private lateinit var viewModel: SearchImageViewModel
    private lateinit var adapter: SearchImageAdapter
    private val listHolder = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_search)


        //instantiating or getting the viewModel reference
        viewModel = ViewModelProvider(this, ((application) as BaseApplication).provider).get(SearchImageViewModel::class.java)

        //setting up the adapter and layout manager of the recycler
        setUpRecycler()

        //observing viewModel for emmited images
        viewModel.searchResponseData().observe(this, Observer {
            showImages(it)
            progress.visibility = View.GONE
        })

        //set on click listener for search button
        imgSearch.setOnClickListener {
            fetchData()
        }

        edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
                fetchData()
            true
        }

    }

    private fun setUpRecycler() {
        adapter = SearchImageAdapter(listHolder)
        recycler.adapter = adapter
        val layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recycler.layoutManager = layoutManager

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (layoutManager.itemCount > 0 && layoutManager.findLastVisibleItemPositions(null).asList().any { it == layoutManager.itemCount || it == layoutManager.itemCount - 1 }) {
                    fetchData()
                }

            }
        })
    }

    private fun showImages(images: List<String>) {
        listHolder.clear()
        listHolder.addAll(images)
        adapter.notifyDataSetChanged()
    }


    private fun fetchData() {
        viewModel.searchImage(edtSearch.text.toString())
        progress.visibility = View.VISIBLE

    }
}
