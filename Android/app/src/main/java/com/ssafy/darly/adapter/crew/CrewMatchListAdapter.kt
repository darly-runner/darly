package com.ssafy.darly.adapter.crew

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
import com.ssafy.darly.activity.MatchLobbyActivity
import com.ssafy.darly.model.MatchDetails

class CrewMatchListAdapter(
    val roomsList: List<MatchDetails>,
    val inflater: LayoutInflater,
    val glide: RequestManager
): RecyclerView.Adapter<CrewMatchListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val hostImg: ImageView
        val goalDistance: TextView
        val matchTitle: TextView
        val hostNickname: TextView
        val currentNum: TextView

        init {
            hostImg = itemView.findViewById(R.id.matchHostImg)
            goalDistance = itemView.findViewById(R.id.goalDistance)
            matchTitle = itemView.findViewById(R.id.matchTitle)
            hostNickname = itemView.findViewById(R.id.hostNickname)
            currentNum = itemView.findViewById(R.id.currentNum)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewMatchListAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.crew_rooms_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CrewMatchListAdapter.ViewHolder, position: Int) {
        glide.load(roomsList.get(position).hostImage).circleCrop().into(holder.hostImg)
        holder.goalDistance.text = roomsList.get(position).matchGoalDistance.toString()
        holder.matchTitle.text = roomsList.get(position).matchTitle
        holder.hostNickname.text = roomsList.get(position).hostNickname
//        holder.currentNum.text = roomsList.get(position).matchCurPerson.toString()
        holder.currentNum.text = roomsList.size.toString()
        val matchId = roomsList.get(position).matchId

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, MatchLobbyActivity::class.java)
            intent.putExtra("matchId", matchId)
            Log.d("chchchch", matchId.javaClass.toString())
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return roomsList.size
    }
}