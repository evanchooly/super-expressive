package com.antwerkz.expression.types

object Word : Type("word") {
    override fun copy() = Word

    override fun evaluate() = "\\w"
}
