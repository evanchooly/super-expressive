package com.antwerkz.expression

interface RegularExpression {
    companion object : RegularExpression {
        override fun anyChar(): RegularExpression {
            return SuperExpressive().anyChar()
        }

        override fun anyOf(body: RegularExpression.() -> RegularExpression): RegularExpression {
            return SuperExpressive().anyOf(body)
        }

        override fun assertAhead(
            body: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().assertAhead(body)
        }

        override fun assertBehind(
            body: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().assertBehind(body)
        }

        override fun assertNotAhead(
            body: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().assertNotAhead(body)
        }

        override fun assertNotBehind(
            body: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().assertNotBehind(body)
        }

        override fun backreference(index: Int): RegularExpression {
            return SuperExpressive().backreference(index)
        }

        override fun capture(body: RegularExpression.() -> RegularExpression): RegularExpression {
            return SuperExpressive().capture(body)
        }

        override fun carriageReturn(): RegularExpression {
            return SuperExpressive().carriageReturn()
        }

        override fun ignoreCase(): RegularExpression {
            return SuperExpressive().ignoreCase()
        }

        override fun multiLine(): RegularExpression {
            return SuperExpressive().multiLine()
        }

        override fun allowComments(): RegularExpression {
            return SuperExpressive().allowComments()
        }

        override fun canonicalEquivalence(): RegularExpression {
            return SuperExpressive().canonicalEquivalence()
        }

        override fun dotAll(): RegularExpression {
            return SuperExpressive().dotAll()
        }

        override fun literal(): RegularExpression {
            return SuperExpressive().literal()
        }

        override fun unixLines(): RegularExpression {
            return SuperExpressive().unixLines()
        }

        override fun char(c: Char): RegularExpression {
            return SuperExpressive().char(c)
        }

        override fun digit(): RegularExpression {
            return SuperExpressive().digit()
        }

        override fun group(body: RegularExpression.() -> RegularExpression): RegularExpression {
            return SuperExpressive().group(body)
        }

        override fun namedBackreference(name: String): RegularExpression {
            return SuperExpressive().namedBackreference(name)
        }

        override fun namedCapture(
            name: String,
            body: RegularExpression.() -> RegularExpression
        ): RegularExpression {
            return SuperExpressive().namedCapture(name, body)
        }

        override fun newline(): RegularExpression {
            return SuperExpressive().newline()
        }

        override fun nonDigit(): RegularExpression {
            return SuperExpressive().nonDigit()
        }

        override fun nonWhitespaceChar(): RegularExpression {
            return SuperExpressive().nonWhitespaceChar()
        }

        override fun nonWord(): RegularExpression {

            return SuperExpressive().nonWord()
        }

        override fun nonWordBoundary(): RegularExpression {
            return SuperExpressive().nonWordBoundary()
        }

        override fun oneOrMore(): RegularExpression {
            return SuperExpressive().oneOrMore()
        }

        override fun oneOrMoreLazy(): RegularExpression {
            return SuperExpressive().oneOrMoreLazy()
        }

        override fun optional(): RegularExpression {
            return SuperExpressive().optional()
        }

        override fun range(start: Char, end: Char): RegularExpression {
            return SuperExpressive().range(start, end)
        }

        override fun startOfInput(): RegularExpression {
            return SuperExpressive().startOfInput()
        }

        override fun endOfInput(): RegularExpression {
            return SuperExpressive().endOfInput()
        }

        override fun string(s: String): RegularExpression {
            return SuperExpressive().string(s)
        }

        override fun tab(): RegularExpression {
            return SuperExpressive().tab()
        }

        override fun whitespaceChar(): RegularExpression {
            return SuperExpressive().whitespaceChar()
        }

        override fun word(): RegularExpression {
            return SuperExpressive().word()
        }

        override fun wordBoundary(): RegularExpression {
            return SuperExpressive().wordBoundary()
        }

        override fun zeroOrMore(): RegularExpression {
            return SuperExpressive().zeroOrMore()
        }

        override fun zeroOrMoreLazy(): RegularExpression {
            return SuperExpressive().zeroOrMoreLazy()
        }

        override fun exactly(count: Int): RegularExpression {
            return SuperExpressive().exactly(count)
        }

        override fun atLeast(count: Int): RegularExpression {
            return SuperExpressive().atLeast(count)
        }

        override fun between(x: Int, y: Int): RegularExpression {
            return SuperExpressive().between(x, y)
        }

        override fun betweenLazy(x: Int, y: Int): RegularExpression {
            return SuperExpressive().betweenLazy(x, y)
        }

        override fun anyOfChars(chars: String): RegularExpression {
            return SuperExpressive().anyOfChars(chars)
        }

        override fun anythingButChars(chars: String): RegularExpression {
            return SuperExpressive().anythingButChars(chars)
        }

        override fun anythingButRange(start: Char, end: Char): RegularExpression {
            return SuperExpressive().anythingButRange(start, end)
        }

        override fun subexpression(
            expr: RegularExpression,
            optionsLambda: SubexpressionOptions.() -> Unit
        ): RegularExpression {
            return SuperExpressive().subexpression(expr, optionsLambda)
        }
    }

    fun anyChar(): RegularExpression

    fun anyOf(body: RegularExpression.() -> RegularExpression): RegularExpression

    fun assertAhead(body: RegularExpression.() -> RegularExpression): RegularExpression

    fun assertBehind(body: RegularExpression.() -> RegularExpression): RegularExpression

    fun assertNotAhead(body: RegularExpression.() -> RegularExpression): RegularExpression

    fun assertNotBehind(body: RegularExpression.() -> RegularExpression): RegularExpression

    fun backreference(index: Int): RegularExpression

    fun capture(body: RegularExpression.() -> RegularExpression): RegularExpression

    fun carriageReturn(): RegularExpression
    fun ignoreCase(): RegularExpression
    fun multiLine(): RegularExpression
    fun allowComments(): RegularExpression
    fun canonicalEquivalence(): RegularExpression
    fun dotAll(): RegularExpression
    fun literal(): RegularExpression
    fun unixLines(): RegularExpression

    fun char(c: Char): RegularExpression

    fun digit(): RegularExpression

    fun group(body: RegularExpression.() -> RegularExpression): RegularExpression

    fun namedBackreference(name: String): RegularExpression

    fun namedCapture(
        name: String,
        body: RegularExpression.() -> RegularExpression
    ): RegularExpression

    fun newline(): RegularExpression

    fun nonDigit(): RegularExpression

    fun nonWhitespaceChar(): RegularExpression

    fun nonWord(): RegularExpression

    fun nonWordBoundary(): RegularExpression

    fun oneOrMore(): RegularExpression
    fun oneOrMoreLazy(): RegularExpression

    fun optional(): RegularExpression

    fun range(start: Char, end: Char): RegularExpression

    //    fun singleLine(): RegularExpression = : RegularExpression

    fun startOfInput(): RegularExpression

    fun endOfInput(): RegularExpression

    fun string(s: String): RegularExpression

    fun tab(): RegularExpression

    fun whitespaceChar(): RegularExpression

    fun word(): RegularExpression

    fun wordBoundary(): RegularExpression

    fun zeroOrMore(): RegularExpression
    fun zeroOrMoreLazy(): RegularExpression
    fun exactly(count: Int): RegularExpression
    fun atLeast(count: Int): RegularExpression

    fun between(x: Int, y: Int): RegularExpression
    fun betweenLazy(x: Int, y: Int): RegularExpression

    fun anyOfChars(chars: String): RegularExpression
    fun anythingButChars(chars: String): RegularExpression

    fun anythingButRange(start: Char, end: Char): RegularExpression

    fun subexpression(
        expr: RegularExpression,
        optionsLambda: SubexpressionOptions.() -> Unit = {}
    ): RegularExpression
}

fun RegularExpression.toRegex(): Regex = (this as SuperExpressive).toRegex()
