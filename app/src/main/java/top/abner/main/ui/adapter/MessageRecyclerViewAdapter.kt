package top.abner.main.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import top.abner.main.data.entity.Message
import android.view.LayoutInflater
import top.abner.main.R

/**
 * 
 * @author Nebula
 * @version 1.0.0
 * @date 2019/3/6 11:30
 */
class MessageRecyclerViewAdapter(list: List<Message>?) : RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder>() {

    private var list: List<Message>? = list


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageRecyclerViewAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: MessageRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.mContentTv.setText(list?.get(position)?.content);
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mContentTv: TextView
        init {
            mContentTv = itemView.findViewById(R.id.content)
        }
    }
}