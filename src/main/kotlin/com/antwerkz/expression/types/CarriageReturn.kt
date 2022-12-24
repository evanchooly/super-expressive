package com.antwerkz.expression.types

internal object CarriageReturn : Type("carriageReturn") {
    override fun copy() = CarriageReturn

    override fun evaluate() = "\\r"
}
