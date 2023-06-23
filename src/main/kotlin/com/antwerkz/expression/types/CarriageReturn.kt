package com.antwerkz.expression.types

internal object CarriageReturn : Type("carriageReturn") {
    init {
        fusible = true
    }
    override fun copy() = CarriageReturn

    override fun evaluate() = "\\r"
}
