package com.antwerkz.expression.types

internal class AtLeast(count: Int) : Quantified("atLeast") {
    init {
        times = listOf(count)
    }

    override fun copy() = AtLeast(times[0]).copy(this)

    override fun evaluate() = evaluate("{${times[0]},}")
}
