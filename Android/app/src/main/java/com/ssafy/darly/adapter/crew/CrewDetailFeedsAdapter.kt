package com.ssafy.darly.adapter.crew

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.model.CrewFeeds
import com.ssafy.darly.model.FeedsList

class CrewDetailFeedsAdapter(
    val feedsList: List<FeedsList>,
    val inflater: LayoutInflater,
    val glide: RequestManager
) : RecyclerView.Adapter<CrewDetailFeedsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val crewFeedImg: ImageView

        init {
            crewFeedImg = itemView.findViewById(R.id.crewFeedImg)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrewDetailFeedsAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.crew_feeds_grid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CrewDetailFeedsAdapter.ViewHolder, position: Int) {
        glide.load(feedsList.get(position).feedImage).into(holder.crewFeedImg)

        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView?.contetxt, )
        }

    }

    override fun getItemCount(): Int {
        return feedsList.size
    }
}