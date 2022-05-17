package com.ssafy.darly.adapter.crew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.ssafy.darly.R
import com.ssafy.darly.model.MatchUsers

class CrewMatchLobbyAdapter(
    val users: List<MatchUsers>,
    val inflater: LayoutInflater,
    val glide: RequestManager
) : RecyclerView.Adapter<CrewMatchLobbyAdapter.ViewHolder>() {

//    var users = mutableListOf<MatchUsers>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImg: ImageView
        val userName: TextView
        val userDistance: TextView
        val userPace: TextView
        val userIsReady: ImageView
        val isHost: ImageView

        init {
            userImg = itemView.findViewById(R.id.userImg)
            userName = itemView.findViewById(R.id.userName)
            userDistance = itemView.findViewById(R.id.userDistance)
            userPace = itemView.findViewById(R.id.userPace)
            userIsReady = itemView.findViewById(R.id.userIsReady)
            isHost = itemView.findViewById(R.id.isHost)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = inflater.inflate(R.layout.crew_match_lobby_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        glide.load(users.get(position).userImage).circleCrop().into(holder.userImg)
        holder.userName.text = users.get(position).userNickname
        holder.userDistance.text = users.get(position).userTotalDistance.toString()
        holder.userPace.text = timeToStr(users.get(position).userPaceAvg.toInt())

        val isReady = users.get(position).userStatus
        when (isReady) {
            "R" -> holder.userIsReady.setImageResource(R.drawable.ic_status_ready)
            "N" -> holder.userIsReady.setImageResource(R.drawable.ic_unready)
        }
        val isUserHost = users.get(position).isHost
        when (isUserHost == 1) {
            true -> {
                holder.isHost.visibility = View.VISIBLE
                holder.userIsReady.setImageResource(R.drawable.ic_status_ready)
            }
            false -> holder.isHost.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

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