package com.example.myapplication.ui.details_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.NearLocationDetails
import com.example.myapplication.utils.toCelsius

class DetailsAdapter : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    var data = listOf<NearLocationDetails>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val otherDayDay: TextView = itemView.findViewById(R.id.otherDayDay)
        private val otherDayTemp: TextView = itemView.findViewById(R.id.otherDayTemp)

        fun bind(item: NearLocationDetails) {
            otherDayDay.text = item.applicableDate
            otherDayTemp.text = item.theTemp?.toCelsius()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.other_day, parent, false)

                return ViewHolder(view)
            }
        }
    }
}