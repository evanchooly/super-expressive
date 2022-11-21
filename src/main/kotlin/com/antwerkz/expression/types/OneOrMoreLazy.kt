package com.antwerkz.expression.types

class OneOrMoreLazy : Qualified("oneOrMoreLazy") {
    override fun evaluate() = evaluate("+?")

    override fun copy() = OneOrMoreLazy().copy(this)
}
