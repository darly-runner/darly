package com.ssafy.darly.adapter.crew.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.model.CrewRecommendations

class CrewRecommendationAdapter(
    val inflater: LayoutInflater,
    val glide: RequestManager
): RecyclerView.Adapter<CrewRecommendationAdapter.ViewHolder>(){
    var myCrewItemList: List<CrewRecommendations>? = null

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val crewRecommendationName: TextView
        val crewRecommendationImg: ImageView
        val crewRecommendationLocation: TextView
        val crewRecommendationMembers: TextView

        init {
            crewRecommendationName = itemView.findViewById(R.id.crewRecommendationName)
            crewRecommendationImg = itemView.findViewById(R.id.crewRecommendationImg)
            crewRecommendationLocation = itemView.findViewById(R.id.crewRecommendationLocation)
            crewRecommendationMembers = itemView.findViewById(R.id.crewRecommendationMembers)
        }

        fun bind(crew: CrewRecommendations) {
            crewRecommendationName.text = crew.crewName
            crewRecommendationLocation.text = crew.crewAddress
            crewRecommendationMembers.text = crew.crewPeopleNum.toString()
            glide.load((crew.crewImage)).into(crewRecommendationImg)
        }
    }
    fun submitList(crewList: List<CrewRecommendations>) {
        myCrewItemList = crewList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrewRecommendationAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.crew_recommendations, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CrewRecommendationAdapter.ViewHolder, position: Int) {
        myCrewItemList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = 2
}