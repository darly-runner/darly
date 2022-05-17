package com.ssafy.darly.adapter.crew

import android.content.res.ColorStateList
import android.graphics.Color
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
        var crewDetailUserRank: TextView

        init {
            crewRankingUserName = itemView.findViewById(R.id.crewRankingUserName)
            userImage = itemView.findViewById(R.id.crewRankingUserImg)
            userDistance = itemView.findViewById(R.id.crewRankingUserDistance)
            userPace = itemView.findViewById(R.id.crewRankingUserPace)
            crewDetailUserRank = itemView.findViewById(R.id.crewDetailUserRank)
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
        holder.userPace.text = timeToStr(crewDetailRankItems.get(position).userPace.toInt())
        val rank = position +1
        holder.crewDetailUserRank.text = rank.toString()
        holder.crewDetailUserRank.backgroundTintList = when(rank == 1) {
            true -> ColorStateList.valueOf(Color.rgb(249, 84,87))
            false -> ColorStateList.valueOf(Color.rgb(176,176,176))
        }
        glide.load(crewDetailRankItems.get(position).userImage).circleCrop().into(holder.userImage)
    }

    override fun getItemCount(): Int = crewDetailRankItems.size

    // time -> hh:mm:ss
    fun timeToStr(t : Int) : String{
        val m = t / 60
        var second = (t % 60).toString()

        val hour = (m / 60).toString()
        var minute = (m % 60).toString()

        if(minute.length == 1)
            minute = "0$minute"

        if(second.length == 1)
            second = "0$second"

        return if(hour != "0")
            "$hour:$minute:$second"
        else
            "$minute:$second"
    }
}