package com.antwerkz.expression.types

internal class OneOrMore : Qualified() {
    override fun evaluate() = evaluate("+")

    override fun copy() = OneOrMore().copy(this)
}
