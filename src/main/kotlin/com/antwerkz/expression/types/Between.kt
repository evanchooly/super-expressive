package com.antwerkz.expression.types

class Between(private val x: Int, private val y: Int) : Quantified("between") {
    init {
        times = listOf(x, y)
    }

    override fun copy() = Between(times[0], times[1]).copy(this)

    override fun evaluate() = evaluate("{${times[0]},${times[1]}}")
}
