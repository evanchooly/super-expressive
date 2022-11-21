package com.antwerkz.expression.types

class Between(private val x: Int, private val y: Int) :
    Type(
        "between",
    ) {
    init {
        times = listOf(x, y)
        containsChildren = true
    }

    override fun copy() = Between(times[0], times[1]).copy(this)

    override fun evaluate(): String {
        TODO("Not yet implemented")
    }
}
