package com.antwerkz.expression.types

class AtLeast(count: Int) : Type("atLeast") {
    init {
        times = listOf(count)
        containsChildren = true
    }

    override fun copy() = AtLeast(times[0]).copy(this)

    override fun evaluate(): String {
        TODO("Not yet implemented")
    }
}
