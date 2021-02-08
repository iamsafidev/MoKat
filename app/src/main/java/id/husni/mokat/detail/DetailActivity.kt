package id.husni.mokat.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.husni.mokat.R
import id.husni.mokat.core.data.source.remote.network.ApiConfig
import id.husni.mokat.core.data.source.remote.response.MoviesItem
import id.husni.mokat.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_DATA = "extra_data"
    }
    private var _binding : ActivityDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.detailToolbar).apply {
            title = getString(R.string.detail)
        }
       supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extraData = intent.getParcelableExtra<MoviesItem>(EXTRA_DATA)
        showDetail(extraData)

    }

    private fun showDetail(extraData: MoviesItem?) {
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}