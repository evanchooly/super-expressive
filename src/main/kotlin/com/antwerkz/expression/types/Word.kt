package com.antwerkz.expression.types

internal object Word : Type("word") {
    init {
        fusible = true
    }
    override fun copy() = Word

    override fun evaluate() = "\\w"
}
