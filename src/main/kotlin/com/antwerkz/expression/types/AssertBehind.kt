package com.antwerkz.expression.types

class AssertBehind : Type("assertBehind") {
    init {
        containsChildren = true
    }
    override fun copy() = AssertBehind().copy(this)

    override fun evaluate(): String {
        val list = value as List<Type>
        val evaluated = list.joinToString("") { it.evaluate() }
        return "(?<=${evaluated})"
    }
}
