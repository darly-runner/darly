package com.ssafy.darly.adapter.crew

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.activity.CrewFeedsDetailActivity
import com.ssafy.darly.model.CrewFeeds
import com.ssafy.darly.model.FeedsList
import kotlin.reflect.typeOf

class CrewDetailFeedsAdapter(
    val feedsList: ArrayList<FeedsList>,
    val inflater: LayoutInflater,
    val glide: RequestManager,
    val crewId: Long,
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
        val feedId = feedsList.get(position).feedId

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CrewFeedsDetailActivity::class.java)
            intent.putExtra("feedId", feedId)
            intent.putExtra("crewId", crewId)
            intent.putExtra("position", position)
            intent.putExtra("object", feedsList)
//            Log.d("feedId", feedId.javaClass)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return feedsList.size
    }
}