package com.antwerkz.expression

@Suppress("unused")
class Types {
    companion object {
        fun root() = Type("root")
        fun noop() = Type("noop")
        fun startOfInput() = Type("startOfInput")
        fun endOfInput() = Type("endOfInput")
        fun anyChar() = Type("anyChar")
        fun whitespaceChar() = Type("whitespaceChar")
        fun nonWhitespaceChar() = Type("nonWhitespaceChar")
        fun digit() = Type("digit")
        fun nonDigit() = Type("nonDigit")
        fun word() = Type("word")
        fun nonWord() = Type("nonWord")
        fun wordBoundary() = Type("wordBoundary")
        fun nonWordBoundary() = Type("nonWordBoundary")
        fun newline() = Type("newline")
        fun carriageReturn() = Type("carriageReturn")
        fun tab() = Type("tab")
        fun nullByte() = Type("nullByte")
        fun anyOfChars() = Type("anyOfChars")
        fun anythingButString() = Type("anythingButString")
        fun anythingButChars() = Type("anythingButChars")
        fun anythingButRange() = Type("anythingButRange")
        fun char(value: String): Type = Type("char", value)
        fun range() = Type("range")
        fun string(value: String) = Type("string", value) { quantifierRequiresGroup = true }

        fun anyOf() = DeferredType("anyOf") { containsChildren = true }
        /*
                val namedBackreference= name => deferredType("namedBackreference", { name }),
                val backreference = index => deferredType("backreference", { index }),
                val capture= deferredType("capture", { containsChildren= true }),
                val subexpression= Type("subexpression", { containsChildren= true, quantifierRequiresGroup= true }),
                val namedCapture= name => deferredType("namedCapture", { name, containsChildren= true }),
                val group= deferredType("group", { containsChildren= true }),
                val assertAhead= deferredType("assertAhead", { containsChildren= true }),
                val assertNotAhead= deferredType("assertNotAhead", { containsChildren= true }),
                val assertBehind= deferredType("assertBehind", { containsChildren= true }),
                val assertNotBehind= deferredType("assertNotBehind", { containsChildren= true }),
                val exactly= times => deferredType("exactly", { times, containsChild= true }),
                val atLeast= times => deferredType("atLeast", { times, containsChild= true }),
                val between= (x, y) => deferredType("between", { times= [x, y], containsChild= true }),
                val betweenLazy= (x, y) => deferredType("betweenLazy", { times= [x, y], containsChild= true }),
                val zeroOrMore= deferredType("zeroOrMore", { containsChild= true }),
                val zeroOrMoreLazy= deferredType("zeroOrMoreLazy", { containsChild= true }),
                val oneOrMore= deferredType("oneOrMore", { containsChild= true }),
                val oneOrMoreLazy= deferredType("oneOrMoreLazy", { containsChild= true }),
                val optional= deferredType("optional", { containsChild= true }),
        */
    }
}

open class Type(val type: String, options: Type.() -> Unit = {}) {
    var quantifierRequiresGroup: Boolean = false
    var value: Any? = null

    constructor(type: String, value: Any, options: Type.() -> Unit = {}): this(type, options) {
        this.value = value
    }

    init {
        this.options()
    }

    override fun toString(): String {
        return "Type(name='$type', quantifierRequiresGroup=$quantifierRequiresGroup)"
    }

    fun value(elements: MutableList<Type>): Type {
        return Type(type, elements) { quantifierRequiresGroup = quantifierRequiresGroup}
    }
}

class DeferredType(type: String, options: DeferredType.() -> Unit = {}): Type(type) {
    var containsChildren: Boolean = false
    init {
        this.options()
    }

    override fun toString(): String {
        return "DeferredType(name='$type', containsChildren=$containsChildren)"
    }

}