package id.husni.mokat.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import id.husni.mokat.favorite.FavoriteActivity
import id.husni.mokat.R
import id.husni.mokat.core.source.Resources
import id.husni.mokat.core.ui.MoviesAdapter
import id.husni.mokat.databinding.ActivityMainBinding
import id.husni.mokat.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        val rvAdapter = MoviesAdapter()
        rvAdapter.onItemClick = { detailData ->
            val i = Intent(this,DetailActivity::class.java)
            i.putExtra(DetailActivity.EXTRA_DATA,detailData)
            startActivity(i)
        }
        with(binding.mainRv){
            layoutManager = GridLayoutManager(context,2)
            setHasFixedSize(true)
            adapter = rvAdapter
        }

        mainViewModel.getAllMovies.observe(this,{ movies->
            if (movies != null){
                when(movies){
                    is Resources.Loading<*> ->{
                        binding.tvEmpty.visibility = View.GONE
                        showProgressBar(true)
                    }
                    is Resources.Success<*> ->{
                        showProgressBar(false)
                        binding.tvEmpty.visibility = View.GONE
                        rvAdapter.setMovies(movies.data)
                    }
                    is Resources.Error<*> ->{
                        showProgressBar(false)
                        binding.tvEmpty.visibility = View.VISIBLE
                        Toast.makeText(this,getString(R.string.wrong),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorites){
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showProgressBar(isShow: Boolean) {
        if (isShow){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}