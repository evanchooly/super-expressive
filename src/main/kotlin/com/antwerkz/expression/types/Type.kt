package com.antwerkz.expression.types

internal abstract class Type(var value: Any? = null) {
    var containsChildren: Boolean = false
    var quantifierRequiresGroup: Boolean = false
    var times: List<Int> = emptyList()
    var name: String? = null
    var index: Int = 0

    constructor(original: Type) : this(original.value) {
        this.name = original.name
        this.index = original.index
        this.times = original.times
        this.containsChildren = original.containsChildren
        this.quantifierRequiresGroup = original.quantifierRequiresGroup
    }

    fun copy(original: Type): Type {
        this.value = original.value
        this.name = original.name
        this.index = original.index
        this.times = original.times
        this.containsChildren = original.containsChildren
        this.quantifierRequiresGroup = original.quantifierRequiresGroup

        return this
    }

    fun value(elements: MutableList<Type>): Type {
        return copy().also { it.value = elements }
    }

    abstract fun copy(): Type

    abstract fun evaluate(): String
}
