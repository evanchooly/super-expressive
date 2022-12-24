package com.antwerkz.expression.types

internal object Tab : Type("tab") {
    override fun copy() = Tab

    override fun evaluate() = "\\t"
}
