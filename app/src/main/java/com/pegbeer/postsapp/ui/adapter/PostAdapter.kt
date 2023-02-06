package com.pegbeer.postsapp.ui.adapter


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.pegbeer.postsapp.data.model.Post
import com.pegbeer.postsapp.databinding.PostItemBinding
import com.pegbeer.postsapp.ui.listener.IOnPostButtonClickListener
import com.pegbeer.postsapp.ui.view.AlbumActivity
import com.pegbeer.postsapp.ui.view.CommentsActivity

class PostAdapter(
    private val listener:IOnPostButtonClickListener
) : RecyclerView.Adapter<PostAdapter.ViewHolder>(), Filterable {

    var sourceList:List<Post> = emptyList()

    private val diffCallback = object : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PostItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Post>) {
        differ.submitList(list)
    }

    inner class ViewHolder (private val binding:PostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Post) = with(binding) {
            titleTextView.text = item.title
            bodyTextView.text = item.body
            albumsIconButton.setOnClickListener {
                listener.onClick(AlbumActivity::class.java, item.id)
            }
            commentsIconButton.setOnClickListener {
                listener.onClick(CommentsActivity::class.java, item.id)
            }
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
                        differ.submitList(results.values as List<Post>)
                    }
                }
            }
        }
    }
}