package com.antwerkz.expression.types
object NonDigit : Type("nonDigit") {
    override fun copy() = NonDigit

    override fun evaluate() = "\\D"
}
