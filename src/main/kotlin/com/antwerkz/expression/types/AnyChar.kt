package com.antwerkz.expression.types

internal object AnyChar : Type("anyChar") {
    override fun copy() = AnyChar

    override fun evaluate() = "."
}
