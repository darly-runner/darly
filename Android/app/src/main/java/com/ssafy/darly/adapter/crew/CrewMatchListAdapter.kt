package com.ssafy.darly.adapter.crew

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.activity.MatchLobbyActivity
import com.ssafy.darly.model.MatchDetails

class CrewMatchListAdapter(
    private val roomsList: List<MatchDetails>,
    val inflater: LayoutInflater,
    val glide: RequestManager
) : RecyclerView.Adapter<CrewMatchListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hostImg: ImageView
        val goalDistance: TextView
        val matchTitle: TextView
        val hostNickname: TextView
        val currentNum: TextView
        val matchDisable: ImageView
        val isStarted: TextView

        init {
            hostImg = itemView.findViewById(R.id.matchHostImg)
            goalDistance = itemView.findViewById(R.id.goalDistance)
            matchTitle = itemView.findViewById(R.id.matchTitle)
            hostNickname = itemView.findViewById(R.id.hostNickname)
            currentNum = itemView.findViewById(R.id.currentNum)
            matchDisable = itemView.findViewById(R.id.matchDisable)
            isStarted = itemView.findViewById(R.id.isStarted)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrewMatchListAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.crew_rooms_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CrewMatchListAdapter.ViewHolder, position: Int) {
        glide.load(roomsList.get(position).hostImage).circleCrop().into(holder.hostImg)
        holder.goalDistance.text = roomsList.get(position).matchGoalDistance.toString()
        holder.matchTitle.text = roomsList.get(position).matchTitle
        holder.hostNickname.text = roomsList.get(position).hostNickname
        holder.currentNum.text = roomsList.get(position).matchCurPerson.toString()
//        holder.currentNum.text = roomsList.size.toString()

        val currentNum = roomsList.get(position).matchCurPerson
        val matchId = roomsList.get(position).matchId
        val matchStatus = roomsList.get(position).matchStatus

        when (matchStatus) {
            "S" -> holder.isStarted.text = "달리기 진행중"
            "W" -> {
                holder.isStarted.visibility = View.GONE
                holder.matchDisable.visibility = View.GONE
                holder.itemView.setOnClickListener {
                    if (currentNum < 6) {
//                    holder.itemView.setOnClickListener {
                        val intent = Intent(holder.itemView.context, MatchLobbyActivity::class.java)
                        intent.putExtra("matchId", matchId)
                        intent.putExtra("matchStatus", matchStatus)
                        ContextCompat.startActivity(holder.itemView.context, intent, null)
//                    }
                    } else {
                        Toast.makeText(holder.itemView.context, "정원이 초과되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return roomsList.size
    }
}