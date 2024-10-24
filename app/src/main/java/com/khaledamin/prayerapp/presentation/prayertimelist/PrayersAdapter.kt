package com.khaledamin.prayerapp.presentation.prayertimelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.khaledamin.prayerapp.R
import com.khaledamin.prayerapp.databinding.ItemPrayerBinding
import com.khaledamin.prayerapp.utils.convertTimeTo12HrFormat

class PrayersAdapter(val oldData: Map<String, String>) :
    Adapter<PrayersAdapter.PrayersViewHolder>() {

    inner class PrayersViewHolder(val binding: ItemPrayerBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PrayersAdapter.PrayersViewHolder {
        return PrayersViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_prayer,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PrayersAdapter.PrayersViewHolder, position: Int) {
        holder.binding.title.text = oldData.keys.elementAt(position)
        holder.binding.prayer.text = convertTimeTo12HrFormat(oldData.values.elementAt(position))
    }

    override fun getItemCount(): Int = oldData.size

    fun updatePrayers(newData: Map<String, String>) {
        val
    }

}