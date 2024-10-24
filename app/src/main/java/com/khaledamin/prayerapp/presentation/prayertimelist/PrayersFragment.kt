package com.khaledamin.prayerapp.presentation.prayertimelist

import android.os.Bundle
import android.view.View
import com.khaledamin.prayerapp.R
import com.khaledamin.prayerapp.databinding.FragmentPrayersBinding
import com.khaledamin.prayerapp.presentation.abstracts.BaseFragment
import java.util.Calendar


class PrayersFragment : BaseFragment<FragmentPrayersBinding,PrayersViewModel>() {
    override val layout: Int
        get() = R.layout.fragment_prayers
    override val viewModelClass: Class<PrayersViewModel>
        get() = PrayersViewModel::class.java

    private lateinit var calendar: Calendar
    private lateinit var prayersAdapter: PrayersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calendar = Calendar.getInstance()
        prayersAdapter = PrayersAdapter(mutableMapOf())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPrayerDays(
            year = calendar.get(Calendar.YEAR),
            month = calendar.get(Calendar.MONTH) + 1,
            latitude =
        )
    }

    override fun setupObservers() {

    }

}