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
import id.husni.mokat.core.utils.MovieDiffCallBack
import id.husni.mokat.databinding.ItemListBinding
import id.husni.mokat.detail.DetailActivity

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val listMovies = ArrayList<MoviesEntity>()

    fun setMovies(items: List<MoviesEntity>?){
        val diffCallBack = MovieDiffCallBack(listMovies,items!!)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        listMovies.clear()
        listMovies.addAll(items)
        diffResult.dispatchUpdatesTo(this)
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
        fun bind(moviesEntity: MoviesEntity) {
            with(binding){
                tvTitle.text = moviesEntity.title
                tvRating.text = moviesEntity.voteAverage.toString()

                Glide.with(itemView.context)
                    .load(ApiConfig.POSTER_URL + moviesEntity.posterPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_broken).error(R.drawable.ic_broken))
                    .into(imgList)
                itemView.setOnClickListener {
                    val i = Intent(itemView.context, DetailActivity::class.java)
                    i.putExtra(DetailActivity.EXTRA_DATA, moviesEntity)
                    itemView.context.startActivity(i)

                }
            }
        }

    }
}