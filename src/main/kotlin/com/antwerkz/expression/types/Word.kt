package com.antwerkz.expression.types

internal object Word : Type("word") {
    override fun copy() = Word

    override fun evaluate() = "\\w"
}
