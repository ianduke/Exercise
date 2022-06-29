package com.example.fitbodinterview.ui.graph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.example.fitbodinterview.R
import com.example.fitbodinterview.utils.Logger
import com.example.fitbodinterview.utils.ThemeExtensions.getTextColorPrimary

class GraphView(
    context: Context,
    attrs: AttributeSet,
) : View(context, attrs) {

    private var xOffset: Int = 0
    private var yOffset: Int = 0

    companion object {
        private const val AXES_LINE_WIDTH = 4f
        private const val DATA_LINE_WIDTH = 7f
        private const val TEXT_SIZE_IN_SP = 16f
    }

    private val dataSet = mutableListOf<GraphPoint>()
    private var xMin: Int = 0
    private var xMax: Int = 0
    private var yMin: Int = 0
    private var yMax: Int = 0

    fun setData(newDataSet: List<GraphPoint>) {
        xMin = (newDataSet.minOf { it.x })
        xMax = (newDataSet.maxOf { it.x })
        yMin = (newDataSet.minOf { it.y })
        yMax = (newDataSet.maxOf { it.y })

        // add padding to the top and bottom of the graph
        val yPadding = (yMax - yMin) * .1
        yMin -= yPadding.toInt()
        yMax += yPadding.toInt()

        xOffset = xMin
        yOffset = yMin

        dataSet.clear()
        dataSet.addAll(newDataSet)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawAxes(canvas)
        drawLabels(canvas)

        dataSet.forEachIndexed { index, exerciseRecord ->
            val realX = exerciseRecord.x.toRealX()
            val realY = exerciseRecord.y.toRealY()
            Logger.d("XXXX: index = $index, realX = $realX, realY = $realY, exercise record = $exerciseRecord")

            if (index < dataSet.size - 1) {
                val nextDataPoint = dataSet[index + 1]
                val endX = nextDataPoint.x.toRealX()
                val endY = nextDataPoint.y.toRealY()
                Logger.d("XXXX: Drawing line from $realX, $realY to $endX, $endY")
                canvas?.drawLine(realX, realY, endX, endY, dataPointLinePaint)
            }

            canvas?.drawCircle(realX, realY, DATA_LINE_WIDTH, dataPointPaint)
        }
    }

    private val dataPointPaint = Paint().apply {
        color = Color.RED
        strokeWidth = DATA_LINE_WIDTH
        style = Paint.Style.FILL
    }

    private val dataPointLinePaint = Paint().apply {
        color = Color.RED
        strokeWidth = DATA_LINE_WIDTH
        isAntiAlias = true
    }

    private val axisLinePaint = Paint().apply {
        color = Color.GRAY
        strokeWidth = AXES_LINE_WIDTH
    }

    private val textPaint = Paint(ANTI_ALIAS_FLAG).apply {
        color = context.getTextColorPrimary()
        val spSize = TEXT_SIZE_IN_SP
        textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spSize,
            resources.displayMetrics
        )
    }

    private fun drawAxes(canvas: Canvas?) {
        canvas?.drawVerticalAxis(paddingStart)
        canvas?.drawVerticalAxis((width - paddingEnd)/3)
        canvas?.drawVerticalAxis((width - paddingEnd)*2/3)
        canvas?.drawVerticalAxis(width - paddingEnd)

        canvas?.drawHorizontalAxis(0 + paddingTop)
        canvas?.drawHorizontalAxis((height - paddingBottom)/3)
        canvas?.drawHorizontalAxis((height - paddingBottom)*2/3)
        canvas?.drawHorizontalAxis(height - paddingBottom)
    }

    private fun drawLabels(canvas: Canvas?) {
        canvas?.drawText(resources.getString(R.string.lbs_format, yMin), paddingStart.toFloat(), (yMin).toRealY(), textPaint)
        canvas?.drawText(resources.getString(R.string.lbs_format, yMax), paddingStart.toFloat(), (yMax).toRealY(), textPaint)
    }

    private fun Int.toRealX() = (toFloat() - xOffset) / (xMax - xOffset) * (width - paddingStart - paddingEnd) + paddingStart
    private fun Int.toRealY() = height - paddingBottom - ((toFloat() - yOffset) / (yMax - yOffset) * (height - paddingTop - paddingBottom))

    private fun Canvas.drawHorizontalAxis(y: Int) {
        drawLine(
            paddingStart.toFloat(),
            y.toFloat(),
            width.toFloat() - paddingEnd,
            y.toFloat(),
            axisLinePaint
        )
    }

    private fun Canvas.drawVerticalAxis(x: Int) {
        drawLine(
            x.toFloat(),
            paddingTop.toFloat(),
            x.toFloat(),
            height.toFloat() - paddingBottom,
            axisLinePaint
        )
    }
}
