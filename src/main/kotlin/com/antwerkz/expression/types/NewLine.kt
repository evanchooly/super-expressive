package com.antwerkz.expression.types

object NewLine : Type("newline") {
    override fun copy() = NewLine

    override fun evaluate() = "\\n"
}
