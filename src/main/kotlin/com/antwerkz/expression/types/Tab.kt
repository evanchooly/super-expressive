package com.antwerkz.expression.types

object Tab : Type("tab") {
    override fun copy() = Tab

    override fun evaluate() = "\\t"
}
