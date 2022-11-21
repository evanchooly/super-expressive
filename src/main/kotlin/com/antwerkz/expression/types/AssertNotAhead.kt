package com.antwerkz.expression.types

class AssertNotAhead : Type("assertNotAhead") {
    init {
        containsChildren = true
    }
    override fun copy() = AssertNotAhead().copy(this)

    override fun evaluate(): String {
        val list = value as List<Type>
        val evaluated = list.joinToString("") { it.evaluate() }
        return "(?!${evaluated})"
    }
}
