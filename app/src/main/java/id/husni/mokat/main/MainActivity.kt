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
import id.husni.mokat.FavoriteActivity
import id.husni.mokat.R
import id.husni.mokat.core.data.source.Resources
import id.husni.mokat.core.ui.MoviesAdapter
import id.husni.mokat.core.ui.ViewModelFactory
import id.husni.mokat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        val rvAdapter = MoviesAdapter()
        with(binding.mainRv){
            layoutManager = GridLayoutManager(context,2)
            setHasFixedSize(true)
            adapter = rvAdapter
        }
        val factory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]
        mainViewModel.getAllMovies().observe(this,{ movies->
            if (movies != null){
                when(movies){
                    is Resources.Loading<*> ->{
                        showProgressBar(true)
                    }
                    is Resources.Success<*> ->{
                        showProgressBar(false)
                        rvAdapter.setMovies(movies.data)
                    }
                    is Resources.Error<*> ->{
                        showProgressBar(false)
                        Toast.makeText(this,"Something Wrong",Toast.LENGTH_SHORT).show()
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
            startActivity(Intent(this,FavoriteActivity::class.java))
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