package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.caverock.androidsvg.SVG
import java.util.Random

class StaffView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var w = 0f
    private var h = 0f

    private var trebleImg: Bitmap

    private var noteImg: Bitmap

    private val paint = Paint(ANTI_ALIAS_FLAG).apply {
        color = ResourcesCompat.getColor(resources, R.color.black, null)
        strokeWidth = 10f
    }

    companion object {
        private const val TOP_SPACING = 400f;
        private const val STAFF_SPACING = 100f;
        private const val EXTRA_LINE_START = 425f
        private const val EXTRA_LINE_END = 675f
    }

    var note = 4

    init {
        val trebleSvg = SVG.getFromResource(resources, R.raw.treble)
        trebleImg = Bitmap.createBitmap(600, 800, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(trebleImg)
        trebleSvg.renderToCanvas(canvas)

        val noteSvg = SVG.getFromResource(resources, R.raw.note)
        noteImg = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
        canvas = Canvas(noteImg)
        noteSvg.documentWidth = 300F
        noteSvg.documentHeight = 300F
        noteSvg.renderToCanvas(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w.toFloat()
        this.h = h.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {
            for(i in 0..4) {
                drawLine(0f, TOP_SPACING + STAFF_SPACING * i, w,
                    TOP_SPACING + STAFF_SPACING * i, paint)
            }
            drawBitmap(trebleImg, -150f, 200f, paint)
            drawBitmap(noteImg, 400f, 50 * note + 50f, paint)

            if(note <= 2)
                drawLine(EXTRA_LINE_START, TOP_SPACING - STAFF_SPACING, EXTRA_LINE_END, TOP_SPACING - STAFF_SPACING, paint)
            if(note == 0)
                drawLine(EXTRA_LINE_START, TOP_SPACING - 2 * STAFF_SPACING, EXTRA_LINE_END, TOP_SPACING - 2 * STAFF_SPACING, paint)
            if(note >= 14)
                drawLine(EXTRA_LINE_START, TOP_SPACING + 5 * STAFF_SPACING, EXTRA_LINE_END, TOP_SPACING + 5 * STAFF_SPACING, paint)
            if(note == 16)
                drawLine(EXTRA_LINE_START, TOP_SPACING + 6 * STAFF_SPACING, EXTRA_LINE_END, TOP_SPACING + 6 * STAFF_SPACING, paint)
        }
    }

    fun newNote() {
        note = (0..16).random();
        invalidate()
    }
}