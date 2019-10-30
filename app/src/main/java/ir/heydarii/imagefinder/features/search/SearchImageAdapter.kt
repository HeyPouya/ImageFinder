package ir.heydarii.imagefinder.features.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.heydarii.imagefinder.R
import kotlinx.android.synthetic.main.image_item.view.*

class SearchImageAdapter(private val list: List<String>) :
    RecyclerView.Adapter<SearchImageAdapter.SearchImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return SearchImageViewHolder(view)
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: SearchImageViewHolder, position: Int) =
        holder.bind(list[position])


    class SearchImageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(image: String) {
            Picasso.get().load(image).into(view.imgItem)
        }

    }
}