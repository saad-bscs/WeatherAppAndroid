package com.example.weatherapp_android.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp_android.R
import com.example.weatherapp_android.models.WeatherModel

class WeatherGridAdapter(private val weatherList: List<WeatherModel>) :
    RecyclerView.Adapter<WeatherGridAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.txtTitle.text = "Humidity"
                holder.txtDescription.text = weatherList[0].current.humidity
            }

            1 -> {
                holder.txtTitle.text = "Wind Speed"
                holder.txtDescription.text = (weatherList[0].current.wind_kph + " km/h")
            }

            2 -> {
                holder.txtTitle.text = "UV"
                holder.txtDescription.text = weatherList[0].current.uv
            }

            3 -> {
                holder.txtTitle.text = "Participation"
                holder.txtDescription.text = (weatherList[0].current.precip_mm + " mm")
            }

            4 -> {
                holder.txtTitle.text = "Local Time"
                holder.txtDescription.text = weatherList[0].location.localtime.split(" ")[1]
            }

            5 -> {
                holder.txtTitle.text = "Local Date"
                holder.txtDescription.text = weatherList[0].location.localtime.split(" ")[0]
            }
        }
    }

    override fun getItemCount(): Int {
        return 6
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        val txtDescription = itemView.findViewById<TextView>(R.id.txtValue)
    }
}