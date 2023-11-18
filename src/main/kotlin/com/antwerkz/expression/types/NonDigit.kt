package com.antwerkz.expression.types
internal object NonDigit : Type("nonDigit") {
    init {
        fusible = true
    }

    override fun copy() = NonDigit

    override fun evaluate() = "\\D"
}
