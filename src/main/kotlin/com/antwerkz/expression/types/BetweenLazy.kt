package com.antwerkz.expression.types

internal class BetweenLazy(private val x: Int, private val y: Int) : Quantified("betweenLazy") {
    init {
        times = listOf(x, y)
    }

    override fun copy() = BetweenLazy(times[0], times[1]).copy(this)

    override fun evaluate() = evaluate("{${times[0]},${times[1]}}?")
}
