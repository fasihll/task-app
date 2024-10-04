package com.otakkanan.taskapp.ui.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.otakkanan.taskapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalendarAdapter(private val context: Context,
                      private val data: ArrayList<Date>,
                      private val currentDate: Calendar,
                      private val changeMonth: Calendar?): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    private var mListener: OnItemClickListener? = null
    private var index = -1
    // This is true only the first time you load the calendar.
    private var selectCurrentDate = true
    private val currentMonth = currentDate[Calendar.MONTH]
    private val currentYear = currentDate[Calendar.YEAR]
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private val selectedDay =
        when {
            changeMonth != null -> changeMonth.getActualMinimum(Calendar.DAY_OF_MONTH)
            else -> currentDay
        }
    private val selectedMonth =
        when {
            changeMonth != null -> changeMonth[Calendar.MONTH]
            else -> currentMonth
        }
    private val selectedYear =
        when {
            changeMonth != null -> changeMonth[Calendar.YEAR]
            else -> currentYear
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.custom_calendar_day, parent, false), mListener!!)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Format hari dalam seminggu untuk Bahasa Indonesia
        val sdfDay = SimpleDateFormat("EEEE", Locale("id", "ID"))
        val cal = Calendar.getInstance()
        cal.time = data[holder.adapterPosition]

        // Format hari dalam seminggu dan tanggal
        val displayMonth = cal[Calendar.MONTH]
        val displayYear = cal[Calendar.YEAR]
        val displayDay = cal[Calendar.DAY_OF_MONTH]

        // Format hari dalam seminggu
        val dayInWeek = sdfDay.format(cal.time)
        holder.txtDayInWeek.text = dayInWeek

        // Format tanggal
        holder.txtDay.text = displayDay.toString()

        // Kondisi pemilihan dan tampilan item
        if (displayYear >= currentYear) {
            if (displayMonth >= currentMonth || displayYear > currentYear) {
                if (displayDay >= currentDay || displayMonth > currentMonth || displayYear > currentYear) {
                    holder.cardLayout.setOnClickListener {
                        index = holder.adapterPosition
                        selectCurrentDate = false
                        holder.listener.onItemClick(holder.adapterPosition)
                        notifyDataSetChanged()
                    }

                    if (index == holder.adapterPosition)
                        makeItemSelected(holder)
                    else {
                        if (displayDay == selectedDay && displayMonth == selectedMonth && displayYear == selectedYear && selectCurrentDate)
                            makeItemSelected(holder)
                        else
                            makeItemDefault(holder)
                    }
                } else makeItemDisabled(holder)
            } else makeItemDisabled(holder)
        } else makeItemDisabled(holder)
    }

    inner class ViewHolder(itemView: View, val listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        var txtDay: TextView = itemView.findViewById(R.id.txt_date)
        var txtDayInWeek: TextView = itemView.findViewById(R.id.txt_day)
        var cardLayout: MaterialCardView = itemView.findViewById(R.id.calendar_linear_layout)
    }

    /**
     * OnClickListener.
     */
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    /**
     * This make the item disabled.
     */
    private fun makeItemDisabled(holder: ViewHolder) {
        holder.txtDay.setTextColor(ContextCompat.getColor(context, R.color.md_theme_onBackground))
        holder.txtDayInWeek.setTextColor(ContextCompat.getColor(context, R.color.md_theme_onBackground))
        holder.cardLayout.setCardBackgroundColor(Color.WHITE)
        holder.cardLayout.isEnabled = false
    }

    /**
     * This make the item selected.
     */
    private fun makeItemSelected(holder: ViewHolder) {
        holder.txtDay.setTextColor(Color.WHITE)
        holder.txtDayInWeek.setTextColor(Color.WHITE)
        holder.cardLayout.setCardBackgroundColor(ContextCompat.getColor(context, R.color.md_theme_primaryContainer))
        Log.d("CalendarAdapter", "Background color set to primary for selected item")
        holder.cardLayout.isEnabled = false
    }

    /**
     * This make the item default.
     */
    private fun makeItemDefault(holder: ViewHolder) {
        holder.txtDay.setTextColor(Color.BLACK)
        holder.txtDayInWeek.setTextColor(Color.BLACK)
        holder.cardLayout.setCardBackgroundColor(Color.WHITE)
        holder.cardLayout.isEnabled = true
    }
}