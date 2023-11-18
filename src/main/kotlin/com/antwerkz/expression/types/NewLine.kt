package com.antwerkz.expression.types

internal object NewLine : Type("newline") {
    init {
        fusible = true
    }

    override fun copy() = NewLine

    override fun evaluate() = "\\n"
}
