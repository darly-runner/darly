package com.ssafy.darly.adapter.crew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.model.Feeds
import com.ssafy.darly.model.FeedsDetail

class CrewFeedsAdapter(
    val feedsDetail: List<Feeds>,
    val inflater: LayoutInflater,
    val glide: RequestManager
): RecyclerView.Adapter<CrewFeedsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val feedHostImg: ImageView
        val feedHostName: TextView
        val feedImage: ImageView
        val feedDate: TextView
        val feedContent: TextView
        val feedComments: TextView

        init {
            feedHostImg = itemView.findViewById(R.id.feedHostImg)
            feedHostName = itemView.findViewById(R.id.feedHostName)
            feedImage = itemView.findViewById(R.id.feedImage)
            feedDate = itemView.findViewById(R.id.feedDate)
            feedContent = itemView.findViewById(R.id.feedContent)
            feedComments = itemView.findViewById(R.id.feedComments)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewFeedsAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.crew_feeds_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        glide.load(feedsDetail.get(position).hostImage).circleCrop().into(holder.feedHostImg)
        holder.feedHostName.text = feedsDetail.get(position).hostNickname
        glide.load(feedsDetail.get(position).feedImage).into(holder.feedImage)
        holder.feedDate.text = feedsDetail.get(position).feedDate
        holder.feedContent.text = feedsDetail.get(position).feedContent
    }

    override fun getItemCount(): Int {
        return feedsDetail.size
    }
}