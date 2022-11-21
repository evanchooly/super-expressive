package com.antwerkz.expression.types

class OneOrMore : Qualified("oneOrMore") {
    override fun evaluate() = evaluate("+")

    override fun copy() = OneOrMore().copy(this)
}
