package com.antwerkz.expression

data class StackFrame(
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
                "cannot quantify regular expression with 'atLeast' because it's already being quantified with '${quantifier.type}'"
            )
        }
        this.quantifier = quantifier
    }
}
