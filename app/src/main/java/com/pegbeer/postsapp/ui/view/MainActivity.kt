package com.pegbeer.postsapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.pegbeer.postsapp.core.SortMode
import com.pegbeer.postsapp.core.Status
import com.pegbeer.postsapp.core.extensions.showErrorDialog
import com.pegbeer.postsapp.databinding.ActivityMainBinding
import com.pegbeer.postsapp.ui.adapter.PostAdapter
import com.pegbeer.postsapp.ui.listener.IOnPostButtonClickListener
import com.pegbeer.postsapp.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity() : ScopeActivity(), IOnPostButtonClickListener {

    private val viewModel:MainViewModel by viewModel()
    private val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter = PostAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch{
            viewModel.posts.collect{
                when(it.status){
                    Status.LOADING ->{
                        showLoading()
                    }
                    Status.ERROR ->{
                        showErrorDialog()
                    }
                    Status.SUCCESS ->{
                        adapter.sourceList = it.data!!
                        adapter.submitList(it.data)
                        observePosts()
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun observePosts() {
        viewModel.searchQuery.observe(this){
            adapter.filter.filter(it)
        }
    }

    private fun hideLoading() {
        binding.mainLoadingIndicator.visibility = View.GONE
        binding.recyclerViewPosts.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.mainLoadingIndicator.visibility = View.VISIBLE
        binding.recyclerViewPosts.visibility = View.GONE
    }

    private fun initViews() {
        binding.recyclerViewPosts.adapter = adapter
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.searchQuery.postValue(text.toString())
        }
    }

    override fun <T : ScopeActivity> onClick(destination: Class<T>, id:Int) {
        Intent(this,destination).apply {
            putExtra(POST_ID,id)
        }.let {
            startActivity(it)
        }
    }

    companion object{
        const val POST_ID = "post_id"
    }
}