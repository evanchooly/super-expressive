package com.antwerkz.expression.types

class AssertNotBehind : Type("assertNotBehind") {
    init {
        containsChildren = true
    }
    override fun copy() = AssertNotBehind().copy(this)

    override fun evaluate(): String {
        val list = value as List<Type>
        val evaluated = list.joinToString("") { it.evaluate() }
        return "(?<!${evaluated})"
    }
}
