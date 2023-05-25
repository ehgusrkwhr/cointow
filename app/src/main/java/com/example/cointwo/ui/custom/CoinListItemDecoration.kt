package com.example.cointwo.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cointwo.R

class CoinListItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val dividerHeight = 2
    private val dividerColor = ContextCompat.getColor(context, R.color.gray)
    private val paint = Paint().apply {
        color = dividerColor
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + dividerHeight

            c.drawRect(
                child.left.toFloat(),
                top.toFloat(),
                child.right.toFloat(),
                bottom.toFloat(),
                paint
            )
        }
    }
}