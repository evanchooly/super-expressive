package com.antwerkz.expression

data class StackFrame(
    val type: Type,
    var quantifier: Type? = null,
    val elements: MutableList<Type> = mutableListOf()
) {
    fun quantifier(quantifier: Type) {
        if (this.quantifier != null) {
            throw IllegalStateException("cannot quantify regular expression with 'atLeast' because it's already being quantified with '${quantifier.type}'");
        }
        this.quantifier =quantifier
    }
}
