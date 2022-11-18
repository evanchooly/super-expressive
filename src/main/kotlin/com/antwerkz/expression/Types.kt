package com.antwerkz.expression

@Suppress("unused")
class Types {
    companion object {
        val root = Type("root")
        val noop = Type("noop")
        val startOfInput = Type("startOfInput")
        val endOfInput = Type("endOfInput")
        val anyChar = Type("anyChar")
        val whitespaceChar = Type("whitespaceChar")
        val nonWhitespaceChar = Type("nonWhitespaceChar")
        val digit = Type("digit")
        val nonDigit = Type("nonDigit")
        val word = Type("word")
        val nonWord = Type("nonWord")
        val wordBoundary = Type("wordBoundary")
        val nonWordBoundary = Type("nonWordBoundary")
        val newline = Type("newline")
        val carriageReturn = Type("carriageReturn")
        val tab = Type("tab")
        val nullByte = Type("nullByte")
        val anyOfChars = Type("anyOfChars")
        val anythingButString = Type("anythingButString")
        val anythingButChars = Type("anythingButChars")
        val anythingButRange = Type("anythingButRange")
        val char = Type("char")
        val range = Type("range")
        val string = Type("string") { quantifierRequiresGroup = true }
        /*
                val namedBackreference= name => deferredType("namedBackreference", { name }),
                val backreference = index => deferredType("backreference", { index }),
                val capture= deferredType("capture", { containsChildren= true }),
                val subexpression= Type("subexpression", { containsChildren= true, quantifierRequiresGroup= true }),
                val namedCapture= name => deferredType("namedCapture", { name, containsChildren= true }),
                val group= deferredType("group", { containsChildren= true }),
                val anyOf= deferredType("anyOf", { containsChildren= true }),
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

class Type(val name: String, options: Type.() -> Unit = {}) {
    var quantifierRequiresGroup: Boolean = false

    init {
        this.options()
    }

    override fun toString(): String {
        return "Type(name='$name', quantifierRequiresGroup=$quantifierRequiresGroup)"
    }
}
