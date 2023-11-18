package com.antwerkz.expression.types

internal abstract class Qualified : Type() {
    init {
        containsChildren = true
    }

    fun evaluate(symbol: String): String {
        val value = this.value as Type
        val inner = value.evaluate()
        val withGroup = if (value.quantifierRequiresGroup) "(?:${inner})" else inner

        return "${withGroup}$symbol"
    }
}
