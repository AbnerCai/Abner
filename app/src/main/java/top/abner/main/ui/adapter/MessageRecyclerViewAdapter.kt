package top.abner.main.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import top.abner.main.data.entity.Message
import android.view.LayoutInflater
import androidx.paging.PagedListAdapter
import top.abner.main.R
import androidx.recyclerview.widget.DiffUtil




/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/6 11:30
 */
class MessageRecyclerViewAdapter(list: List<Message>?)
        : PagedListAdapter<Message, MessageRecyclerViewAdapter.ViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageRecyclerViewAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageRecyclerViewAdapter.ViewHolder, position: Int) {
        val data = getItem(position)
        holder.mContentTv.setText(data?.content);
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mContentTv: TextView
        init {
            mContentTv = itemView.findViewById(R.id.content)
        }
    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see android.support.v7.util.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean =
                    oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean =
                    oldItem == newItem
        }
    }
}