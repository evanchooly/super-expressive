package com.antwerkz.expression.types

object Root : Type("root") {
    override fun copy() = Root

    override fun evaluate(): String {
        TODO("Shouldn't ever get called")
    }
}
