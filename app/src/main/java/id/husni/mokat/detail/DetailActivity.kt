package id.husni.mokat.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.husni.mokat.R
import id.husni.mokat.core.data.source.local.entity.MoviesEntity
import id.husni.mokat.core.data.source.remote.network.ApiConfig
import id.husni.mokat.core.ui.ViewModelFactory
import id.husni.mokat.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_DATA = "extra_data"
    }
    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailMoviesViewModel: DetailMoviesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.detailToolbar).apply {
            title = getString(R.string.detail)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val factory = ViewModelFactory.getInstance(this)
        detailMoviesViewModel = ViewModelProvider(this,factory)[DetailMoviesViewModel::class.java]
        val extraData = intent.getParcelableExtra<MoviesEntity>(EXTRA_DATA)
        showDetail(extraData)

    }

    private fun showDetail(extraData: MoviesEntity?) {
        with(binding){
            tvTitle.text = extraData?.title
            tvRating.text = extraData?.voteAverage.toString()
            tvRelease.text = extraData?.releaseDate
            tvOverview.text = extraData?.overview
        }
        Glide.with(this)
            .load(ApiConfig.POSTER_URL + extraData?.posterPath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_broken).error(R.drawable.ic_broken))
            .into(binding.imgDetail)
        Glide.with(this)
            .load(ApiConfig.POSTER_URL + extraData?.backdropPath)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_broken).error(R.drawable.ic_broken))
            .into(binding.imgBackDetail)

        var statusFavorite = extraData?.isFavorite
        setStatusFav(statusFavorite)
        binding.btnFavorite.setOnClickListener {
            statusFavorite = !statusFavorite!!
            detailMoviesViewModel.setFavourite(extraData!!, statusFavorite!!)
            setStatusFav(statusFavorite)
        }



    }

    private fun setStatusFav(statusFavorite: Boolean?) {
        if (statusFavorite == true){
            binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_favorite))
        }
        else{
            binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_unfavorite))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}