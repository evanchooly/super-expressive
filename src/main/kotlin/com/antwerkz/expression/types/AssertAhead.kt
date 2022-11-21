package com.antwerkz.expression.types

import com.antwerkz.expression.types.Root.containsChildren

class AssertAhead : Type("assertAhead") {
    init {
        containsChildren = true
    }
    override fun copy() = AssertAhead().copy(this)

    override fun evaluate(): String {
        val list = value as List<Type>
        val evaluated = list.joinToString("") { it.evaluate() }
        return "(?=${evaluated})"
    }
}
