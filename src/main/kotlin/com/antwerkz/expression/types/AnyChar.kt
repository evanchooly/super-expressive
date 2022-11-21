package com.antwerkz.expression.types

object AnyChar : Type("anyChar") {
    override fun copy() = AnyChar

    override fun evaluate() = "."
}
