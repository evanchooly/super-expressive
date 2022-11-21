package com.antwerkz.expression.types

class BetweenLazy(private val x: Int, private val y: Int) : Type("betweenLazy") {
    init {
        times = listOf(x, y)
        containsChildren = true
    }

    override fun copy() = BetweenLazy(times[0], times[1]).copy(this)

    override fun evaluate(): String {
        TODO("Not yet implemented")
    }
}
