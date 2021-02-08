package id.husni.mokat.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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
        showProgressBar(true)
        mainViewModel.getAllMovies.observe(this,{movies->
            rvAdapter.setMovies(movies)
            showProgressBar(false)
        })
    }

    private fun showProgressBar(isShow: Boolean) {
        if (isShow){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}