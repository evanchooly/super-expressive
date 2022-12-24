package com.antwerkz.expression.types

internal class NamedBackReference(private val backReference: String) : Type("namedBackreference") {
    init {
        name = backReference
    }

    override fun copy() = NamedBackReference(name!!).copy(this)

    override fun evaluate() = "\\k<${name}>"
}
