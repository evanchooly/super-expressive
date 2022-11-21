package com.antwerkz.expression.types

class BackReference(private val refIndex: Int) : Type("backreference") {
    init {
        index = refIndex
    }

    override fun copy() = BackReference(index).copy(this)

    override fun evaluate() = "\\${index}"
}
