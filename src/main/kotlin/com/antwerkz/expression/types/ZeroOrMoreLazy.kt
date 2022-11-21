package com.antwerkz.expression.types

class ZeroOrMoreLazy : Qualified("zeroOrMoreLazy") {
    override fun copy() = ZeroOrMoreLazy().copy(this)

    override fun evaluate() = evaluate("*?")
}
