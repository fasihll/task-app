package com.otakkanan.taskapp.ui.main.kalender


import android.view.View
import androidx.core.content.ContextCompat
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.otakkanan.taskapp.R
import com.otakkanan.taskapp.data.model.TaskDay
import com.otakkanan.taskapp.databinding.CardDayBinding
import java.time.LocalDate

class CalendarAdapter : DayBinder<CalendarAdapter.CalendarViewHolder> {

    var listener: CalendarListener? = null

    private var selectedDate: LocalDate = LocalDate.now()
    private var taskDay: List<TaskDay> = emptyList()

    inner class CalendarViewHolder(private val binding: CardDayBinding) : ViewContainer(binding.root) {

        private lateinit var day: CalendarDay

        init {
            binding.root.setOnClickListener {
                if (day.owner == DayOwner.THIS_MONTH) {
                    if (selectedDate != day.date) {
                        listener?.onDateClick(day.date)
                    }
                }
            }
        }

        fun bind(model: CalendarDay) {
            binding.apply {
                this@CalendarViewHolder.day = model

                if (model.owner == DayOwner.THIS_MONTH) {
                    dayT.text = model.date.dayOfMonth.toString()
                    card.setBackgroundResource(if (selectedDate == model.date) R.drawable
                        .background_selected else R.drawable.background_unselected)
                    dayT.setTextColor(
                        if (selectedDate == model.date)
                            ContextCompat.getColor(binding.root.context, R.color.md_theme_onPrimary)
                        else
                            ContextCompat.getColor(binding.root.context, R.color.md_theme_onBackground)
                    )

                    val taskCount = taskDay.count { it.date == model.date }

                    countTask.text = binding.root.context.getString(R.string.count_task_title,
                        taskCount.toString())

                    when{
                        taskCount == 1 ->{
                            countTask.setTextColor(ContextCompat.getColor(binding.root.context, R
                                .color
                                .colorOnPurpleContainer))
                            statusContainer.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R
                                .color
                                .colorPurpleContainer))
                        }
                        taskCount == 2 ->{
                            countTask.setTextColor(ContextCompat.getColor(binding.root.context, R
                                .color
                                .colorOnBlueContainer))
                            statusContainer.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R
                                .color
                                .colorBlueContainer))
                        }
                        taskCount >= 3 ->{
                            countTask.setTextColor(ContextCompat.getColor(binding.root.context, R
                                .color
                                .colorOnGreenContainer))
                            statusContainer.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R
                                .color
                                .colorGreenContainer))
                        }
                        taskCount == 0 -> {
                            statusContainer.visibility = View.INVISIBLE
                        }

                    }

                    root.visibility = View.VISIBLE
                } else {
                    root.visibility = View.VISIBLE
                    dayT.text = model.date.dayOfMonth.toString()
                    dayT.setTextColor(ContextCompat.getColor(binding.root.context, R.color
                        .md_theme_secondaryContainer))
                    statusContainer.visibility = View.INVISIBLE

                }
            }
        }
    }

    override fun create(view: View): CalendarViewHolder {
        return CalendarViewHolder(CardDayBinding.bind(view))
    }

    override fun bind(container: CalendarViewHolder, day: CalendarDay) {
        container.bind(day)
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun submitSelectedDate(selectedDate: LocalDate) {
        val formerSelectedDate = this.selectedDate
        this.selectedDate = selectedDate
        listener?.onDateChange(formerSelectedDate)
        listener?.onDateChange(selectedDate)
    }

    fun submitTaskDay(taskDay: List<TaskDay>) {
        this.taskDay = taskDay
        taskDay.forEach { taskd ->
            listener?.onDateChange(taskd.date!!)
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // MISC
    ///////////////////////////////////////////////////////////////////////////

    interface CalendarListener {
        fun onDateChange(date: LocalDate)
        fun onDateClick(date: LocalDate)
    }
}