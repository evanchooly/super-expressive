package com.antwerkz.expression.types

internal abstract class Quantified(type: String) : Type(type) {
    init {
        containsChildren = true
    }

    fun evaluate(times: String): String {
        val type = this.value as Type
        val inner = type.evaluate()
        val withGroup = if (type.quantifierRequiresGroup) "(?:${inner})" else inner
        return "${withGroup}${times}"
    }
}
