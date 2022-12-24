package com.antwerkz.expression.types

internal class Group : Type("group") {
    init {
        containsChildren = true
    }
    override fun copy() = Group().copy(this)

    override fun evaluate(): String {
        val value = value as List<Type>
        val evaluated = value.joinToString("") { it.evaluate() }
        return "(?:${evaluated})"
    }
}
