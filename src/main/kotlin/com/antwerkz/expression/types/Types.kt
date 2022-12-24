package com.antwerkz.expression.types

import com.antwerkz.expression.types.Root.value

@Suppress("unused")
internal class Types {
    companion object {
        fun root() = Root
        fun noop() = Noop
        fun startOfInput() = StartOfInput
        fun endOfInput() = EndOfInput
        fun anyChar() = AnyChar
        fun whitespaceChar() = WhitespaceChar
        fun nonWhitespaceChar() = NonWhiteSpaceChar
        fun digit() = Digit
        fun nonDigit() = NonDigit
        fun word() = Word
        fun nonWord() = NonWord
        fun wordBoundary() = WordBoundary
        fun nonWordBoundary() = NonWordBoundary
        fun newline() = NewLine
        fun carriageReturn() = CarriageReturn
        fun tab() = Tab
        fun nullByte() = NullByte
        fun anyOfChars(chars: String) = AnyOfChars(chars)
        fun anythingButChars(chars: String) = AnythingButChars(chars)
        fun anythingButRange(start: Char, end: Char) = AnythingButRange(start, end)
        fun char(value: String) = CharType(value)
        fun range(start: Char, end: Char) = RangeType(start, end)
        fun string(value: String) = StringType(value)
        fun anyOf() = AnyOf()
        fun namedBackreference(backReference: String) = NamedBackReference(backReference)
        fun backreference(refIndex: Int) = BackReference(refIndex)
        fun capture() = Capture()
        fun namedCapture(captureName: String) = NamedCapture(captureName)
        fun group() = Group()
        fun assertAhead() = AssertAhead()
        fun assertNotAhead() = AssertNotAhead()
        fun assertBehind() = AssertBehind()
        fun assertNotBehind() = AssertNotBehind()
        fun subexpression(list: List<Type>) = Subexpression(list)
        fun exactly(count: Int) = Exactly(count)
        fun atLeast(count: Int) = AtLeast(count)
        fun between(x: Int, y: Int) = Between(x, y)
        fun betweenLazy(x: Int, y: Int) = BetweenLazy(x, y)
        fun zeroOrMore() = ZeroOrMore()
        fun zeroOrMoreLazy() = ZeroOrMoreLazy()
        fun oneOrMore() = OneOrMore()
        fun oneOrMoreLazy() = OneOrMoreLazy()
        fun optional() = OptionalType()
    }
}
