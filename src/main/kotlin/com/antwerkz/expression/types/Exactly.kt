package com.antwerkz.expression.types

class Exactly(count: Int) : Type("exactly") {
    init {
        times = listOf(count)
        containsChildren = true
    }

    override fun copy() = Exactly(times[0]).copy(this)

    override fun evaluate(): String {
        TODO("Not yet implemented")
    }
}
