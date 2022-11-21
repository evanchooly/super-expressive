package com.antwerkz.expression.types

@Suppress("UNCHECKED_CAST")
class Subexpression(private val list: List<Type>) : Type(list) {
    init {
        containsChildren = true
        quantifierRequiresGroup = true
    }

    override fun copy() = Subexpression(value as List<Type>).copy(this)

    override fun evaluate(): String {
        return (value as List<Type>).joinToString("") { it.evaluate() }
    }
}
