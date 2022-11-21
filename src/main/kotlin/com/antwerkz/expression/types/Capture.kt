package com.antwerkz.expression.types

class Capture : Type("capture") {
    init {
        containsChildren = true
    }
    override fun copy() = Capture().copy(this)

    override fun evaluate(): String {
        val list = value as List<Type>
        val evaluated = list.joinToString("") { it.evaluate() }
        return "(${evaluated})"
    }
}
