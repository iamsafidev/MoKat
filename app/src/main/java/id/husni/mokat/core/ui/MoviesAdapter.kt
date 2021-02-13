package id.husni.mokat.core.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.husni.mokat.R
import id.husni.mokat.core.data.source.local.entity.MoviesEntity
import id.husni.mokat.core.data.source.remote.network.ApiConfig
import id.husni.mokat.core.domain.model.Movies
import id.husni.mokat.core.utils.MovieDiffCallBack
import id.husni.mokat.databinding.ItemListBinding
import id.husni.mokat.detail.DetailActivity

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val listMovies = ArrayList<Movies>()

    fun setMovies(items: List<Movies>?){
        if (items == null) return
        listMovies.clear()
        listMovies.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount(): Int = listMovies.size

    class ViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: Movies) {
            with(binding){
                tvTitle.text = movies.title
                tvRating.text = movies.voteAverage.toString()

                Glide.with(itemView.context)
                    .load(ApiConfig.POSTER_URL + movies.posterPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_broken).error(R.drawable.ic_broken))
                    .into(imgList)
                itemView.setOnClickListener {
                    val i = Intent(itemView.context, DetailActivity::class.java)
                    i.putExtra(DetailActivity.EXTRA_DATA, movies)
                    itemView.context.startActivity(i)

                }
            }
        }

    }
}