package com.antwerkz.expression.types

class NamedBackReference(private val backReference: String) : Type("namedBackreference") {
    init {
        name = backReference
    }

    override fun copy() = NamedBackReference(name!!).copy(this)

    override fun evaluate(): String {
        TODO("Not yet implemented")
    }
}
