package com.otakkanan.taskapp.ui.main.kalender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.model.OutDateStyle
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import com.otakkanan.taskapp.databinding.FragmentKalenderBinding
import com.otakkanan.taskapp.ui.main.beranda.TaskAdapter
import com.otakkanan.taskapp.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@AndroidEntryPoint
class KalenderFragment : Fragment(), CalendarAdapter.CalendarListener {

    companion object {
        fun instance(): KalenderFragment {
            return KalenderFragment()
        }
    }

    private var _binding: FragmentKalenderBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<KalenderViewModel>()

    @Inject
    lateinit var headerAdapter: CalendarHeaderAdapter

    @Inject
    lateinit var adapter: CalendarAdapter

    @Inject
    lateinit var dataAdapter: DataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentKalenderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
        observe()
        button()
    }

    private fun updateUI() {
        calendar()
        recyclerview()
    }

    private fun observe() {
        observeSelectedDate()
        observeTreatments()
        observeSurveys()
        observeData()
    }

    private fun button() {
        onScroll()
        onNext()
        onBefore()
    }

    private fun calendar() {
        binding.apply {
            adapter.listener = this@KalenderFragment
            val currentMonth = YearMonth.now()
            calendar.setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), Helper.daysOfWeekFromLocale().first())
            calendar.scrollToMonth(currentMonth)
            calendar.inDateStyle = InDateStyle.ALL_MONTHS
            calendar.outDateStyle = OutDateStyle.END_OF_ROW
            calendar.dayBinder = adapter
            calendar.monthHeaderBinder = headerAdapter
        }
    }

    private fun recyclerview() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(),layoutManager.orientation)
        binding.recyclerview.addItemDecoration(itemDecoration)
        binding.recyclerview.adapter = dataAdapter
    }

    private fun observeSelectedDate() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.selectedDate.collectLatest { data ->
                adapter.submitSelectedDate(data)
            }
        }
    }

    private fun observeTreatments() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.treatments.collectLatest { data ->
//                val treatments = data.groupBy { it.time }.mapValues {}
                adapter.submitTreatments(data)
            }
        }
    }

    private fun observeSurveys() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.surveys.collectLatest { data ->
//                val surveys = data.groupBy { it.time }.mapValues {}
                adapter.submitSurveys(data)
            }
        }
    }

    private fun observeData() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.data.collectLatest { data ->
                dataAdapter.submitList(data)
            }
        }
    }

    private fun onScroll() {
        binding.apply {
            calendar.monthScrollListener = { month ->
                monthT.text = Helper.formatMonthYear(month)
            }
        }
    }

    private fun onNext() {
        binding.apply {
            navigateNextB.setOnClickListener {
                calendar.findFirstVisibleMonth()?.let { month ->
                    calendar.smoothScrollToMonth(month.yearMonth.next)
                }
            }
        }
    }

    private fun onBefore() {
        binding.apply {
            navigateBeforeB.setOnClickListener {
                calendar.findFirstVisibleMonth()?.let { month ->
                    calendar.smoothScrollToMonth(month.yearMonth.previous)
                }
            }
        }
    }

    override fun onDateChange(date: LocalDate) {
        binding.calendar.notifyDateChanged(date)
    }

    override fun onDateClick(date: LocalDate) {
        viewmodel.onSelectedDateChange(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}