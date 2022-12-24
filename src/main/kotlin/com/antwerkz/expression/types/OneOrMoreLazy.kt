package com.antwerkz.expression.types

internal class OneOrMoreLazy : Qualified() {
    override fun evaluate() = evaluate("+?")

    override fun copy() = OneOrMoreLazy().copy(this)
}
