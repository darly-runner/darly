package com.ssafy.darly.adapter.challenge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.model.ChallengeDetail

class ChallengesListAdapter(
    val challengesList: List<ChallengeDetail>,
    val inflater: LayoutInflater,
    val glide: RequestManager
) : RecyclerView.Adapter<ChallengesListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val challengeImg: ImageView
        val challengeName: TextView

        init {
            challengeImg = itemView.findViewById(R.id.challengeImg)
            challengeName = itemView.findViewById(R.id.challengeName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.challenge_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.challengeName.text = challengesList.get(position).eventTitle
        glide.load(challengesList.get(position).eventImage).into(holder.challengeImg)
    }

    override fun getItemCount(): Int {
        return challengesList.size
    }
}