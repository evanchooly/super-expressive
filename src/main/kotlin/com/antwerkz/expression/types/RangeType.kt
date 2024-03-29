package com.antwerkz.expression.types

internal class RangeType(start: Char, end: Char) : Type(listOf(start, end)) {
    init {
        fusible = true
    }

    @Suppress("UNCHECKED_CAST")
    override fun copy(): Type {
        val list = value as List<Char>
        return RangeType(list[0], list[1]).copy(this)
    }

    override fun evaluate(): String {
        val list = value as List<*>
        return "[${list[0]}-${list[1]}]"
    }
}
