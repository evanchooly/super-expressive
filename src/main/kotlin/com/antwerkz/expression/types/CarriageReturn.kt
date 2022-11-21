package com.antwerkz.expression.types

object CarriageReturn : Type("carriageReturn") {
    override fun copy() = CarriageReturn

    override fun evaluate() = "\\r"
}
