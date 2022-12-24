package com.antwerkz.expression.types

internal class ZeroOrMore : Qualified() {
    override fun evaluate() = evaluate("*")
    override fun copy() = ZeroOrMore().copy(this)
}
