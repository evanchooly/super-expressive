package com.antwerkz.expression.types

class ZeroOrMore : Qualified("zeroOrMore") {
    override fun evaluate() = evaluate("*")
    override fun copy() = ZeroOrMore().copy(this)
}
