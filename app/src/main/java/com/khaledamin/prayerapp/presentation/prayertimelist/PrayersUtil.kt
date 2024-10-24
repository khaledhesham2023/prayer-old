package com.khaledamin.prayerapp.presentation.prayertimelist

import androidx.recyclerview.widget.DiffUtil

class PrayersUtil : DiffUtil.ItemCallback<Map<String,String>>() {
    override fun areItemsTheSame(
        oldItem: Map<String, String>,
        newItem: Map<String, String>,
    ): Boolean {
        return oldItem.keys == newItem.keys
    }

    override fun areContentsTheSame(
        oldItem: Map<String, String>,
        newItem: Map<String, String>,
    ): Boolean {
        if (oldItem.size != newItem.size) {
            return false
        }

        for (key in oldItem.keys) {
            if (oldItem[key] != newItem[key]) {
                return false
            }
        }

        return true    }

}