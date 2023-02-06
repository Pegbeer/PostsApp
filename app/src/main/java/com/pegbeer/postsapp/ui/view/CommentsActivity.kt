package com.pegbeer.postsapp.ui.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.pegbeer.postsapp.core.Status
import com.pegbeer.postsapp.core.extensions.showErrorDialog
import com.pegbeer.postsapp.databinding.ActivityCommentsBinding
import com.pegbeer.postsapp.ui.adapter.CommentsAdapter
import com.pegbeer.postsapp.ui.viewmodel.CommentsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentsActivity : ScopeActivity() {

    private val binding by lazy { ActivityCommentsBinding.inflate(layoutInflater) }
    private val viewModel:CommentsViewModel by viewModel()
    private val adapter = CommentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.comments.collect{
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
                        observeComments()
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun observeComments() {
        viewModel.searchQuery.observe(this){
            adapter.filter.filter(it)
        }
    }

    private fun hideLoading() {
        binding.commentsRecyclerview.visibility = View.VISIBLE
        binding.commentsLoadingIndicator.visibility = View.GONE
    }

    private fun showLoading() {
        binding.commentsRecyclerview.visibility = View.GONE
        binding.commentsLoadingIndicator.visibility = View.VISIBLE
    }

    private fun initViews() {
        binding.commentsToolBar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.searchCommentEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.searchQuery.postValue(text.toString())
        }
        binding.commentsRecyclerview.adapter = adapter
        val id = intent.getIntExtra(MainActivity.POST_ID,0)
        viewModel.load(id)
    }

}