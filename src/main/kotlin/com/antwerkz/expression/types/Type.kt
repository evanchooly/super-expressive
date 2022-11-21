package com.antwerkz.expression.types

open class Type(val type: String, var value: Any? = null, options: Type.() -> Unit = {}) {
    var containsChildren: Boolean = false
    var quantifierRequiresGroup: Boolean = false
    var times: List<Int> = emptyList()
    var name: String? = null
    var index: Int = 0

    constructor(original: Type) : this(original.type, original.value) {
        this.name = original.name
        this.index = original.index
        this.times = original.times
        this.containsChildren = original.containsChildren
        this.quantifierRequiresGroup = original.quantifierRequiresGroup
    }

    init {
        this.options()
    }
    fun value(elements: MutableList<Type>): Type {
        return Type(this).also { it.value = elements }
    }

    open fun copy(): Type {
        return Type(this)
    }
}
