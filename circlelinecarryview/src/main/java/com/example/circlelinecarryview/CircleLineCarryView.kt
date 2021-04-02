package com.example.circlelinecarryview

import android.view.View
import android.view.MotionEvent
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF

val colors : Array<Int> = arrayOf(
    "#f44336",
    "#2196F3",
    "#E65100",
    "#64DD17",
    "#6200EA"
).map {
    Color.parseColor(it)
}.toTypedArray()
val parts : Int = 4
val scGap : Float = 0.02f / parts
val strokeFactor : Float = 90f
val sizeFactor : Float = 5.9f
val backColor : Int = Color.parseColor("#BDBDBD")
val delay : Long = 20

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawCircleLineCarry(scale : Float, w : Float, h : Float, paint : Paint) {
    val size : Float = Math.min(w, h) / sizeFactor
    val sf : Float = scale.sinify()
    val sf1 : Float = sf.divideScale(0, parts)
    val sf2 : Float = sf.divideScale(1, parts)
    val sf3 : Float = sf.divideScale(2, parts)
    val x : Float = -w / 2 + (w /2 - size) * sf2
    val y : Float = (-h / 2 + size) * sf2
    save()
    translate(w / 2, h / 2)
    drawLine(-w / 2, 0f, x - (w / 2 - size) * sf3, 0f, paint)
    drawArc(RectF(x - size, y - size, x + size, y + size), 0f, 360f * sf1, true, paint)
    drawLine(0f, -h / 2, 0f, -h / 2 + (h / 2 - size) * sf2 + y, paint)
    restore()
}

fun Canvas.drawCLCNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawCircleLineCarry(scale, w, h, paint)
}
