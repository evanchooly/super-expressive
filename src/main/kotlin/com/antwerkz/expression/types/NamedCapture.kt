package com.antwerkz.expression.types

class NamedCapture(private val captureName: String) : Type("namedCapture") {
    init {
        name = captureName
        containsChildren = true
    }

    override fun copy() = NamedCapture(captureName).copy(this)

    override fun evaluate(): String {
        val list = value as List<Type>
        val evaluated = list.joinToString("") { it.evaluate() }
        return "(?<${name}>${evaluated})"
    }
}
