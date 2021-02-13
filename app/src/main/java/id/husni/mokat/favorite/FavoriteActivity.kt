package id.husni.mokat.favorite

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import id.husni.mokat.R
import id.husni.mokat.core.ui.MoviesAdapter
import id.husni.mokat.core.ui.ViewModelFactory
import id.husni.mokat.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private var _binding : ActivityFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.favoriteToolbar)
        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rvAdapter = MoviesAdapter()

        val factory = ViewModelFactory.getInstance(this)
        favoriteViewModel = ViewModelProvider(this,factory)[FavoriteViewModel::class.java]
        favoriteViewModel.getAllFavorite().observe(this,{
            rvAdapter.setMovies(it)
            binding.tvEmpty.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.GONE

        })

        with(binding.favoriteRv){
            layoutManager = GridLayoutManager(context,2)
            setHasFixedSize(true)
            adapter = rvAdapter
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}