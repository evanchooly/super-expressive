package com.antwerkz.expression.types

internal class ZeroOrMoreLazy : Qualified() {
    override fun copy() = ZeroOrMoreLazy().copy(this)

    override fun evaluate() = evaluate("*?")
}
