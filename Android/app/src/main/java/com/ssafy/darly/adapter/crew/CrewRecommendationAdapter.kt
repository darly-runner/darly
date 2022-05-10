package com.ssafy.darly.adapter.crew.main

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.activity.CrewDetailActivity
import com.ssafy.darly.model.CrewRecommendations

class CrewRecommendationAdapter(
    val myCrewRecommendationList: List<CrewRecommendations>,
    val inflater: LayoutInflater,
    val glide: RequestManager
): RecyclerView.Adapter<CrewRecommendationAdapter.ViewHolder>(){

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
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrewRecommendationAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.crew_recommendations, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CrewRecommendationAdapter.ViewHolder, position: Int) {
        holder.crewRecommendationName.text = myCrewRecommendationList.get(position).crewName
        holder.crewRecommendationLocation.text = myCrewRecommendationList.get(position).crewAddress
        holder.crewRecommendationMembers.text = myCrewRecommendationList.get(position).crewPeopleNum.toString()
        glide.load((myCrewRecommendationList.get(position).crewImage)).into(holder.crewRecommendationImg)
        val crewId = myCrewRecommendationList.get(position).crewId

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, CrewDetailActivity::class.java)

            intent.putExtra("crewId", crewId)
            Log.d("crewId", crewId.javaClass.name)
            Log.d("crewId", "${ myCrewRecommendationList.get(position).crewId }")
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = myCrewRecommendationList.size
}