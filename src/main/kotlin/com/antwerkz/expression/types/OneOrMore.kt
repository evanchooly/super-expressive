package com.antwerkz.expression.types

class OneOrMore : Qualified() {
    override fun evaluate() = evaluate("+")

    override fun copy() = OneOrMore().copy(this)
}
