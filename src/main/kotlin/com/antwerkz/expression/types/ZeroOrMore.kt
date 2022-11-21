package com.antwerkz.expression.types

class ZeroOrMore : Qualified() {
    override fun evaluate() = evaluate("*")
    override fun copy() = ZeroOrMore().copy(this)
}
