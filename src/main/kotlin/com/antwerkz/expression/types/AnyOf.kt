package com.antwerkz.expression.types

class AnyOf : Type("anyOf") {
    init {
        containsChildren = true
    }

    override fun copy() = AnyOf().copy(this)

    override fun evaluate(): String {
        TODO("Not yet implemented")
    }
}
