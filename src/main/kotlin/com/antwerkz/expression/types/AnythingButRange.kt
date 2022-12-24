package com.antwerkz.expression.types

internal class AnythingButRange(start: Char, end: Char) : Type(listOf(start, end)) {
    override fun copy(): Type {
        val list = value as List<Char>
        return AnythingButRange(list[0], list[1]).copy(this)
    }

    override fun evaluate(): String {
        val list = value as List<*>
        return "[^${list[0]}-${list[1]}]"
    }
}
