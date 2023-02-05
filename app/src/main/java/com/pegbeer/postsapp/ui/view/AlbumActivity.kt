package com.pegbeer.postsapp.ui.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.pegbeer.postsapp.core.Status
import com.pegbeer.postsapp.core.extensions.showErrorDialog
import com.pegbeer.postsapp.databinding.ActivityAlbumBinding
import com.pegbeer.postsapp.ui.adapter.AlbumAdapter
import com.pegbeer.postsapp.ui.viewmodel.AlbumsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumActivity : ScopeActivity() {

    private val binding by lazy { ActivityAlbumBinding.inflate(layoutInflater) }
    private val viewModel:AlbumsViewModel by viewModel()

    private val adapter = AlbumAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch{
            viewModel.photos.collect{
                when(it.status){
                    Status.LOADING ->{
                        showLoading()
                    }
                    Status.ERROR ->{
                        showErrorDialog()
                    }
                    Status.SUCCESS ->{
                        adapter.submitList(it.data!!)
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.albumsRecyclerview.visibility = View.GONE
        binding.albumLoadingIndicator.visibility = View.VISIBLE
    }

    private fun hideLoading(){
        binding.albumsRecyclerview.visibility = View.VISIBLE
        binding.albumLoadingIndicator.visibility = View.GONE
    }

    private fun initViews() {
        binding.albumsToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val id = intent.getIntExtra(MainActivity.POST_ID,0)
        binding.albumsRecyclerview.adapter = adapter
        viewModel.load(id)
    }


}