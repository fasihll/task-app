package com.otakkanan.taskapp.component

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.*

class TimePicker @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var radius = 0f
    private var selectedHour = 0
    private var selectedMinute = 0
    private var isHourMode = true // Flag to track hour or minute selection

    init {
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        radius = (min(width, height) / 2 * 0.8).toFloat()
        val centerX = width / 2f
        val centerY = height / 2f

        // Draw the outer circle (C)
        paint.color = Color.parseColor("#E0E7FF") // Light purple background
        paint.setShadowLayer(8f, 0f, 0f, Color.parseColor("#B0A0FF"))
        canvas.drawCircle(centerX, centerY, radius * 1.2f, paint)

        // Draw the middle circle (B)
        paint.color = Color.parseColor("#D3D9FF") // Lighter purple
        canvas.drawCircle(centerX, centerY, radius, paint)

        // Draw the inner circle (A)
        paint.color = Color.WHITE
        canvas.drawCircle(centerX, centerY, radius * 0.8f, paint)

        // Draw the numbers for hours in two circles
        if (isHourMode) {
            paint.color = Color.BLACK
            paint.textSize = 36f
            // Hours 1 to 12 in outer circle
            for (i in 1..12) {
                val angle = Math.toRadians((i * 30 - 90).toDouble())
                val x = (centerX + radius * 1.2f * cos(angle)).toFloat()
                val y = (centerY + radius * 1.2f * sin(angle)).toFloat()
                canvas.drawText(i.toString(), x, y + paint.textSize / 3, paint)
            }

            // Hours 13 to 24 in inner circle
            for (i in 13..24) {
                val angle = Math.toRadians((i * 30 - 90).toDouble())
                val x = (centerX + radius * 0.8f * cos(angle)).toFloat()
                val y = (centerY + radius * 0.8f * sin(angle)).toFloat()
                canvas.drawText(i.toString(), x, y + paint.textSize / 3, paint)
            }

            // Draw the selected hour indicator with text
            paint.color = Color.parseColor("#4F46E5") // Dark purple
            paint.style = Paint.Style.FILL_AND_STROKE
            paint.strokeWidth = 4f
            val selectedAngle = Math.toRadians(((selectedHour % 12) * 30 - 90).toDouble())
            val selectedRadius = if (selectedHour < 13) radius * 1.2f else radius * 0.8f
            val selectedX = (centerX + selectedRadius * cos(selectedAngle)).toFloat()
            val selectedY = (centerY + selectedRadius * sin(selectedAngle)).toFloat()
            canvas.drawCircle(selectedX, selectedY, 40f, paint)
            paint.color = Color.WHITE
            paint.textSize = 24f
            canvas.drawText(selectedHour.toString(), selectedX, selectedY + paint.textSize / 3, paint)
        } else {
            // Draw the numbers for minutes
            paint.color = Color.BLACK
            paint.textSize = 24f
            for (i in 0..59 step 5) {
                val angle = Math.toRadians((i * 6 - 90).toDouble())
                val x = (centerX + radius * 0.8f * cos(angle)).toFloat()
                val y = (centerY + radius * 0.8f * sin(angle)).toFloat()
                canvas.drawText(i.toString(), x, y + paint.textSize / 3, paint)
            }

            // Draw the selected minute indicator with text
            paint.color = Color.parseColor("#4F46E5") // Dark purple
            paint.style = Paint.Style.FILL_AND_STROKE
            paint.strokeWidth = 4f
            val selectedAngle = Math.toRadians((selectedMinute * 6 - 90).toDouble())
            val selectedX = (centerX + radius * 0.8f * cos(selectedAngle)).toFloat()
            val selectedY = (centerY + radius * 0.8f * sin(selectedAngle)).toFloat()
            canvas.drawCircle(selectedX, selectedY, 40f, paint)
            paint.color = Color.WHITE
            paint.textSize = 24f
            canvas.drawText(selectedMinute.toString(), selectedX, selectedY + paint.textSize / 3, paint)
        }

        // Draw the selected time in the center
        paint.color = Color.BLACK
        paint.textSize = 80f
        canvas.drawText(String.format("%02d:%02d", selectedHour, selectedMinute), centerX, centerY + paint.textSize / 3, paint)

        // Draw mode indicator
        paint.textSize = 30f
        paint.color = Color.RED
        canvas.drawText(if (isHourMode) "Hour Mode" else "Minute Mode", centerX, centerY + radius * 1.4f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val centerX = width / 2f
        val centerY = height / 2f
        val angle = atan2(event.y - centerY, event.x - centerX).toDouble()
        val distance = sqrt((event.x - centerX) * (event.x - centerX) + (event.y - centerY) * (event.y - centerY))

        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                if (isHourMode) {
                    // Determine which circle is touched based on distance
                    val radiusThreshold = radius * 0.9f // Adjust this threshold if necessary
                    if (distance < radius * 0.8f) {
                        // Inner circle (13-24)
                        selectedHour = ((Math.toDegrees(angle) + 360) % 360 / 30).toInt() + 12
                    } else if (distance < radius * 1.2f) {
                        // Outer circle (1-12)
                        selectedHour = ((Math.toDegrees(angle) + 360) % 360 / 30).toInt() + 1
                    }
                    selectedHour = (selectedHour % 24)
                } else {
                    // Determine which circle is touched for minutes (assuming only one circle for minutes)
                    selectedMinute = ((Math.toDegrees(angle) + 360) % 360 / 6).toInt()
                    selectedMinute = (selectedMinute % 60)
                }
                invalidate()
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (isHourMode) {
                    isHourMode = false // Switch to minute selection
                } else {
                    isHourMode = true // Switch back to hour selection
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}
