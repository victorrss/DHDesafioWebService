package br.com.eaglecode.desafiowebservice.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.eaglecode.desafiowebservice.R
import br.com.eaglecode.desafiowebservice.model.Result
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_hq.view.*

class HQAdapter(
    val hqs: ArrayList<Result>,
    val listener: OnClickItemListener
) : RecyclerView.Adapter<HQAdapter.ItemHQ>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHQ {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hq, parent, false)
        return ItemHQ(itemView)
    }

    override fun onBindViewHolder(holder: ItemHQ, position: Int) {
        val hq = hqs[position]

        holder.HQNumber.text = "#${hq.id}"
        Glide.with(holder.imgCover.context)
            .load("${hq.thumbnail.path}.${hq.thumbnail.extension}")
            .placeholder(R.drawable.ic_baseline_photo_camera_24)
            .error(R.drawable.ic_baseline_broken_image_24)
            .fallback(R.drawable.ic_baseline_image_not_supported_24)
            .into(holder.imgCover)
    }

    inner class ItemHQ(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val HQNumber: TextView = itemView.tvItemHQNum
        val imgCover: ImageView = itemView.ivItemCover

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listener.OnClickItem(position)
        }
    }

    override fun getItemCount() = hqs.size
}

interface OnClickItemListener {
    fun OnClickItem(position: Int)
}