package com.example.myapplication.ui.near_location_fragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.NearLocation


class NearLocationAdapter constructor(private val onClick: (nearLocation: NearLocation) -> Unit): RecyclerView.Adapter<NearLocationAdapter.ViewHolder>() {

    var data =  listOf<NearLocation>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onClick)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){

        val nearLocationName : TextView = itemView.findViewById(R.id.locationName)
        val nearLocationDistance : TextView = itemView.findViewById(R.id.locationDistance)



        fun bind(item: NearLocation, onClick: (nearLocation: NearLocation) -> Unit) {

            nearLocationName.text = item.title
            nearLocationDistance.text = item.distance.toString()+" Meters"
            itemView.setOnClickListener { onClick(item) }

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.near_location_item, parent, false)

                return ViewHolder(view)
            }
        }
    }
}