package com.antwerkz.expression.types
internal object NonDigit : Type("nonDigit") {
    override fun copy() = NonDigit

    override fun evaluate() = "\\D"
}
