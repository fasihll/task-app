package com.otakkanan.taskapp.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.otakkanan.taskapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class TimePicker @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var hour = 12 // Default hour
    var minute = 0 // Default minute
    var clockMode = ClockMode.HOUR
        set(value) {
            field = value
            invalidate()
        }
    var timeMode = TimeMode.AM
        set(value) {
            field = value
            invalidate()
        }

    private var timeChangedListener: TimeChangeListener? = null

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.md_theme_secondaryFixed)
    }
    private val paintTime = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.md_theme_onSecondary)
    }
    private val numberPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics
        )
        color = context.getColor(R.color.md_theme_onBackground)
    }

    private val dotAndHandPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.md_theme_primary)
        strokeWidth = 5f
    }
    private val blackDot = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = minOf(width, height) / 2 * 0.8f

        // Draw the white circle for the clock face
        canvas.drawCircle(centerX, centerY, radius, paint)
        canvas.drawCircle(centerX, centerY, (radius / 2), paintTime)


        val itemCount = if (clockMode == ClockMode.HOUR) 12 else 60

        // Draw all the highlight circles
        for (i in 1..itemCount) {
            val angle = Math.toRadians((i * 360.0 / itemCount) - 90)
            val x = (centerX + cos(angle) * radius * 0.85).toFloat()
            val y = (centerY + sin(angle) * radius * 0.85).toFloat()

            if (clockMode == ClockMode.HOUR && i == hour || clockMode == ClockMode.MINUTE && (i == minute || minute == 0 && i == 60)) {
                canvas.drawCircle(x, y, 55f, dotAndHandPaint) // Highlight selected hour/minute
                if (clockMode == ClockMode.MINUTE && i % 5 != 0) canvas.drawCircle(
                    x, y, 5f, blackDot
                )
            }
        }

        // Draw all the highlight circles
        for (i in 13..24) {
            val angle = Math.toRadians((i * 360.0 / itemCount) - 90)
            val x = (centerX + cos(angle) * radius * 0.65).toFloat()
            val y = (centerY + sin(angle) * radius * 0.65).toFloat()

            if (clockMode == ClockMode.HOUR && i == hour) {
                canvas.drawCircle(x, y, 55f, dotAndHandPaint) // Highlight selected hour/minute
            }
        }

        // Draw all the text
        for (i in 1..itemCount) {
            val angle = Math.toRadians((i * 360.0 / itemCount) - 90)
            val x = (centerX + cos(angle) * radius * 0.85).toFloat()
            val y = (centerY + sin(angle) * radius * 0.85).toFloat()

            val displayText = if (clockMode == ClockMode.HOUR) i.toString()
            else if (i == 60) "0" // when minute is 60, set 0
            else if (i % 5 == 0) i.toString() // display only 5, 10, 15 .. 55
            else "" // not display smaller minutes such as 1, 2, 3, 4, 6, 7, 8, 9 etc.

            // Check if this is the selected hour or minute
            if ((clockMode == ClockMode.HOUR && i == hour) ||
                (clockMode == ClockMode.MINUTE && (i == minute || minute == 0 && i == 60))
            ) {
                numberPaint.color = Color.WHITE // Change text color to white
            } else {
                numberPaint.color = context.getColor(R.color.md_theme_onBackground) // Reset to original color
            }
            if (displayText.isNotBlank()) {
                canvas.drawText(displayText, x, y + numberPaint.textSize / 3, numberPaint)
            }
        }
        // Draw all the text
        for (i in 13..24) {
            val angle = Math.toRadians((i * 360.0 / itemCount) - 90)
            val x = (centerX + cos(angle) * radius * 0.65).toFloat()
            val y = (centerY + sin(angle) * radius * 0.65).toFloat()

            val displayText = if (clockMode == ClockMode.HOUR) i.toString()
            else ""
            // Check if this is the selected hour
            if (clockMode == ClockMode.HOUR && i == hour) {
                numberPaint.color = Color.WHITE // Change text color to white
            } else {
                numberPaint.color = context.getColor(R.color.md_theme_onBackground) // Reset to original color
            }
            if (displayText.isNotBlank()) {
                if (i == 24) {
                    canvas.drawText("00", x, y + numberPaint.textSize / 3, numberPaint)
                } else canvas.drawText(displayText, x, y + numberPaint.textSize / 3, numberPaint)
            }
        }

        // Draw the hand and the central dot as before
        val handAngle =
            Math.toRadians(((if (clockMode == ClockMode.HOUR) hour % 12 else minute % 60) * 360.0 / itemCount) - 90)
        val handEndX = (centerX + cos(handAngle) * (radius * 0.8 - 27)).toFloat()
        val handEndY = (centerY + sin(handAngle) * (radius * 0.8 - 27)).toFloat()
//        canvas.drawLine(centerX, centerY, handEndX, handEndY, dotAndHandPaint)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y
        val centerX = width / 2f
        val centerY = height / 2f

        val width = width.toFloat()
        val height = height.toFloat()
        val radius = width.coerceAtMost(height) / 2 * 0.9f
        val radius2 = width.coerceAtMost(height) / 2 * 0.4f

        // Calculate the distance from the touch point to the center of the clock
        val distance = sqrt(
            (touchX - centerX).toDouble().pow(2.0) + (touchY - centerY).toDouble().pow(2.0)
        )

        // Only react to touch events within the circular clock face
        if (distance <= radius) {
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    // When a touch is detected, request the parent ScrollView to not intercept touch events
                    parent?.requestDisallowInterceptTouchEvent(true)

                    // Calculate the angle between the touch point and the horizontal line from the center
                    val angleRad = atan2(
                        (touchY - centerY).toDouble(), (touchX - centerX).toDouble()
                    ) + Math.PI / 2
                    var angleDeg = Math.toDegrees(angleRad)

                    // Adjust the angle degree to be positive
                    if (angleDeg < 0) {
                        angleDeg += 360
                    }

                    when (clockMode) {
                        ClockMode.HOUR -> {
                            // Convert angle to hour (12-hour format)
                            var selectedHour = Math.round(angleDeg / 15).toInt() % 24
                            if (selectedHour == 0) selectedHour = 24

                            setTime(selectedHour, minute) // Update the selected hour
                        }

                        ClockMode.MINUTE -> {
                            // Convert angle to minute (60 minute format)
                            val selectedMinute = Math.round(angleDeg / 6)
                                .toInt() % 60 // Converts 0-360 degrees to 0-59 minutes
                            setTime(hour, selectedMinute) // Update the selected minute
                        }
                    }

                    return true
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // When the touch is lifted or cancelled, allow the parent ScrollView to intercept touch events again
                    parent?.requestDisallowInterceptTouchEvent(false)


                    //the followings are for automatically switching from Hour to Minute
                    scope.launch {
                        delay(100)
                        clockMode = ClockMode.MINUTE
                        setTime(hour, minute)
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun setTime(hour: Int, minute: Int) {
        this.hour = hour
        this.minute = minute
        invalidate() // Redraw the view with the new time
        timeChangedListener?.onTimeChanged(hour, minute)
    }

    fun setTimeChangedListener(listener: TimeChangeListener) {
        this.timeChangedListener = listener
    }

    interface TimeChangeListener {
        fun onTimeChanged(hour: Int, minute: Int)
    }

    enum class ClockMode {
        HOUR, MINUTE
    }

    enum class TimeMode {
        AM, PM
    }
    fun switchClockMode(mode: ClockMode) {
        clockMode = mode
        invalidate()
    }
}