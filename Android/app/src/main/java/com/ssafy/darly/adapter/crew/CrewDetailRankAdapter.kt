package com.ssafy.darly.adapter.crew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.model.CrewSummaryRankings

class CrewDetailRankAdapter(
    val crewDetailRankItems: List<CrewSummaryRankings>,
    val inflater: LayoutInflater,
    val glide: RequestManager
) : RecyclerView.Adapter<CrewDetailRankAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val crewRankingUserName: TextView
        var userImage: ImageView
        var userDistance: TextView
        var userPace: TextView

        init {
            crewRankingUserName = itemView.findViewById(R.id.crewRankingUserName)
            userImage = itemView.findViewById(R.id.crewRankingUserImg)
            userDistance = itemView.findViewById(R.id.crewRankingUserDistance)
            userPace = itemView.findViewById(R.id.crewRankingUserPace)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrewDetailRankAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.crew_ranking_items, parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: CrewDetailRankAdapter.ViewHolder, position: Int) {
        holder.crewRankingUserName.text = crewDetailRankItems.get(position).userNickname
        holder.userDistance.text = crewDetailRankItems.get(position).userDistance
        holder.userPace.text = crewDetailRankItems.get(position).userPace.toString()
        glide.load(crewDetailRankItems.get(position).userImage).circleCrop().into(holder.userImage)
    }

    override fun getItemCount(): Int = crewDetailRankItems.size
}