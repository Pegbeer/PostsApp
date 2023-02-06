package com.pegbeer.postsapp.ui.adapter

import com.pegbeer.postsapp.data.model.Comment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.pegbeer.postsapp.databinding.CommentItemBinding

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.ViewHolder>(), Filterable {

    var sourceList:List<Comment> = emptyList()

    private val diffCallback = object : DiffUtil.ItemCallback<Comment>() {

        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Comment>) {
        differ.submitList(list)
    }

    class ViewHolder(
        private val binding: CommentItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comment) = with(binding) {
            if(adapterPosition % 2 == 0){
                commentLinearLayout.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    startToStart = commentContainer.id
                    endToStart = commentGuidelineEnd.id
                }
            }else{
                commentLinearLayout.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    startToEnd = commentGuidelineStart.id
                    endToEnd = commentContainer.id
                }
            }
            commentBodyTextView.text = item.body
            commentEmailTextView.text = item.email
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence): FilterResults {
                return if(constraint.isNotEmpty()){
                    val filtered = sourceList.asSequence().filter { it.body.contains(constraint) }.toList()
                    FilterResults().apply { values = filtered }
                }else{
                    FilterResults().apply { values = sourceList.sortedBy { it.id }.toList() }
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if(results != null){
                    if(results.values != null){
                        differ.submitList(results.values as List<Comment>)
                    }
                }
            }
        }
    }
}