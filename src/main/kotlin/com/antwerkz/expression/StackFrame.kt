package com.antwerkz.expression

import com.antwerkz.expression.types.Type

internal data class StackFrame(
    val type: Type,
    var quantifier: Type? = null,
    var elements: MutableList<Type> = mutableListOf()
) {
    constructor(
        original: StackFrame
    ) : this(
        original.type.copy(),
        original.quantifier?.copy(),
        original.elements.map { it.copy() }.toMutableList()
    )
    fun quantifier(quantifier: Type) {
        if (this.quantifier != null) {
            throw IllegalStateException(
                "cannot quantify regular expression with '${quantifier.javaClass.simpleName}' because it's already being quantified with " +
                    "'${quantifier.javaClass.simpleName}'"
            )
        }
        this.quantifier = quantifier
    }
}
