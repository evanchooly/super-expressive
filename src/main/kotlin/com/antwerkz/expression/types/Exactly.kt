package com.antwerkz.expression.types

internal class Exactly(count: Int) : Quantified("exactly") {
    init {
        times = listOf(count)
    }

    override fun copy() = Exactly(times[0]).copy(this)

    override fun evaluate() = evaluate("{${times[0]}}")
}
