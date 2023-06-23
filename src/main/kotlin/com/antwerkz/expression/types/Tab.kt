package com.antwerkz.expression.types

internal object Tab : Type("tab") {
    init {
        fusible = true
    }
    override fun copy() = Tab

    override fun evaluate() = "\\t"
}
