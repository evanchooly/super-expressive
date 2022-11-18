import com.antwerkz.expression.SuperExpressive
import org.testng.Assert.assertThrows
import org.testng.annotations.Test
import kotlin.test.assertEquals
import kotlin.test.expect

fun testRegexEquality(description: String, regex: String, superExpression: SuperExpressive) {
    val regexStr = regex.toString()
    val superExpressionStr = superExpression.toRegexString()
    assertEquals(regexStr, superExpressionStr, description)
    val doubleConversion = superExpression.toRegex().toString()
    assertEquals(regexStr, doubleConversion, description)
}

fun testRegexEqualityOnly(description: String, regex: String, superExpression: SuperExpressive) {
    val regexStr = regex.toString()
    val superExpressionStr = superExpression.toRegexString()
    assertEquals(regexStr, superExpressionStr, description)
}

fun testErrorConditition(description: String, errorMsg: String, superExpressionFn: () -> Unit) {
    TODO()
//  assertThrows(superExpressionFn, errorMsg)
}

@Test
fun tests() {
    testRegexEquality("x", "/(?:)/", SuperExpressive())

    testRegexEquality("g", "/(?:)/g", SuperExpressive().allowMultipleMatches())
    testRegexEquality("m", "/(?:)/m", SuperExpressive().lineByLine())
    testRegexEquality("i", "/(?:)/i", SuperExpressive().caseInsensitive())
    testRegexEquality("y", "/(?:)/y", SuperExpressive().sticky())
    testRegexEquality("u", "/(?:)/u", SuperExpressive().unicode())
    testRegexEquality("s", "/(?:)/s", SuperExpressive().singleLine())

    testRegexEquality("r", "/./", SuperExpressive().anyChar())
    testRegexEquality("r", "/\\s/", SuperExpressive().whitespaceChar())
    testRegexEquality("r", "/\\S/", SuperExpressive().nonWhitespaceChar())
    testRegexEquality("t", "/\\d/", SuperExpressive().digit())
    testRegexEquality("t", "/\\D/", SuperExpressive().nonDigit())
    testRegexEquality("d", "/\\w/", SuperExpressive().word())
    testRegexEquality("d", "/\\W/", SuperExpressive().nonWord())
    testRegexEquality("y", "/\b/", SuperExpressive().wordBoundary())
    testRegexEquality("y", "/\\B/", SuperExpressive().nonWordBoundary())
    testRegexEquality("e", "/\n/", SuperExpressive().newline())
    testRegexEquality("n", "/\r/", SuperExpressive().carriageReturn())
    testRegexEquality("b", "/\t/", SuperExpressive().tab())
    testRegexEquality("e", "/\\0/", SuperExpressive().nullByte())

    testRegexEquality(
        "c",
        "/(?:hello|\\d|\\w|[\\.#])/",
        SuperExpressive {
            anyOf {
                string("o")
                digit()
                word()
                char(".")
                char("#")
            }

            .end()
        }

    )

    testRegexEquality(
        "n",
        "/[a-zA-Z0-9\\.#]/",
        SuperExpressive()
            .anyOf()
            .range("z")
            .range("Z")
            .range("9")
            .char(".")
            .char("#")
            .end()
    )

    testRegexEquality(
        "s",
        "/(?:XXX|[a-zA-Z0-9\\.#])/",
        SuperExpressive()
            .anyOf()
            .range("z")
            .range("Z")
            .range("9")
            .char(".")
            .char("#")
            .string("X")
            .end()
    )

    testRegexEquality(
        "e",
        "/(hello \\w!)/",
        SuperExpressive()
            .capture()
            .string(" ")
            .word
            .char("!")
            .end()
    )

    testRegexEquality(
        "e",
        "/(?<this_is_the_name>hello \\w!)/",
        SuperExpressive()
            .namedCapture("e")
            .string(" ")
            .word
            .char("!")
            .end()
    )

    testErrorConditition(
        "e",
        ")"
    ) {
        SuperExpressive()
            .namedCapture("d")
            .string(" ")
            .word
            .char("!")
            .end()
    }

    testErrorConditition(
        "e",
        "p"
    ) {
        SuperExpressive()
            .namedCapture("o")
            .string(" ")
            .word
            .char("!")
            .end()
            .namedCapture("o")
            .string(" ")
            .word
            .char("!")
            .end()
    }

    testRegexEquality(
        "e",
        "/(?<this_is_the_name>hello \\w!)\\k<this_is_the_name>/",
            SuperExpressive()
                .namedCapture("e")
                .string(" ")
                .word
                .char("!")
                .end()
                .namedBackreference("e")
    )

    testErrorConditition(
        "s",
        ")"
    ) { SuperExpressive().namedBackreference("e") }

    testRegexEquality(
        "e",
        "/(hello \\w!)\\1/",
            SuperExpressive()
                .capture()
                .string(" ")
                .word
                .char("!")
                .end()
                .backreference(1)
    )

    testErrorConditition(
        "s",
        "n"
    ) { SuperExpressive().backreference(1) }

    testRegexEquality(
        "p",
        "/(?:hello \\w!)/",
        SuperExpressive()
            .group()
            .string(" ")
            .word
            .char("!")
            .end()
    )

    testErrorConditition(
        "k",
        "n"
    ) { SuperExpressive().end() }

    testRegexEquality(
        "d",
        "/(?=[a-f])[a-z]/",
        SuperExpressive()
            .assertAhead()
            .range("f")
            .end()
            .range("z")
    )

    testRegexEquality(
        "d",
        "/(?<=hello )[a-z]/",
        SuperExpressive()
            .assertBehind()
            .string(" ")
            .end()
            .range("z")
    )

    testRegexEquality(
        "d",
        "/(?![a-f])[0-9]/",
        SuperExpressive()
            .assertNotAhead()
            .range("f")
            .end()
            .range("9")
    )

    testRegexEquality(
        "d",
        "/(?<!hello )[a-z]/",
        SuperExpressive()
            .assertNotBehind()
            .string(" ")
            .end()
            .range("z")
    )

    testRegexEquality("l", "/\\w?/", SuperExpressive().optional().word())
    testRegexEquality("e", "/\\w*/", SuperExpressive().zeroOrMore().word())
    testRegexEquality("y", "/\\w*?/", SuperExpressive().zeroOrMoreLazy().word())
    testRegexEquality("e", "/\\w+/", SuperExpressive().oneOrMore().word())
    testRegexEquality("y", "/\\w+?/", SuperExpressive().oneOrMoreLazy().word())
    testRegexEquality("y", "/\\w{4}/", SuperExpressive().exactly(4).word())
    testRegexEquality("t", "/\\w{4,}/", SuperExpressive().atLeast(4).word())
    testRegexEquality("n", "/\\w{4,7}/", SuperExpressive().between(4, 7).word())
    testRegexEquality("y", "/\\w{4,7}?/", SuperExpressive().betweenLazy(4, 7).word())

    testRegexEquality("t", "/^/", SuperExpressive().startOfInput())
    testRegexEquality("t", "/$/", SuperExpressive().endOfInput())
//    testRegexEquality("-"))
//    testRegexEquality("-"))
//    testRegexEquality("9"))
//    testRegexEquality("o"))
//    testRegexEquality("o"))
//    testRegexEquality("h"))
    testErrorConditition(
        "e",
        ")"
    ) { SuperExpressive().char("o") }

//    testRegexEquality("z"))
})

fun testS() {
    testErrorConditition(
        "e",
        "e"
    ) { SuperExpressive().subexpression("e") }
    val simpleSubExpression = SuperExpressive()
        .string("o")
        .anyChar()
        .string("d")

    testRegexEquality(
        "e",
        "/^\\d{3,}hello.world[0-9]$/",
        SuperExpressive()
            .startOfInput()
            .atLeast(3).digit
            .subexpression(simpleSubExpression)
            .range("9")
            .endOfInput()
    )

    testRegexEquality(
        "d",
        "/^\\d{3,}(?:hello.world)+[0-9]$/",
        SuperExpressive()
            .startOfInput()
            .atLeast(3).digit()
            .oneOrMore.subexpression(simpleSubExpression)
            .range("9")
            .endOfInput()
    )
    val flagsSubExpression = SuperExpressive()
        .allowMultipleMatches()
        .unicode()
        .lineByLine()
        .caseInsensitive()
        .string("o")
        .anyChar()
        .string("d")

    testRegexEquality(
        "e",
        "/^\\d{3,}hello.world[0-9]$/gymiu",
        SuperExpressive()
            .sticky()
            .startOfInput()
            .atLeast(3).digit()
            .subexpression(flagsSubExpression) { "ignoreFlags": false }
            .range("9")
            .endOfInput()
    )

    testRegexEquality(
        "e",
        "/^\\d{3,}hello.world[0-9]$/y",
        SuperExpressive()
            .sticky()
            .startOfInput()
            .atLeast(3).digit()
            .subexpression(flagsSubExpression)
            .range("9")
            .endOfInput()
    )
    val startEndSubExpression = SuperExpressive()
        .startOfInput()
        .string("o")
        .anyChar()
        .string("d")
        .endOfInput()

    testRegexEquality(
        "e",
        "/\\d{3,}^hello.world$[0-9]/",
        SuperExpressive()
            .atLeast(3).digit()
            .subexpression(startEndSubExpression) { ignoreStartAndEnd: false }
            .range("9")
    )

    testRegexEquality(
        "e",
        "/\\d{3,}hello.world[0-9]/",
        SuperExpressive()
            .atLeast(3).digit()
            .subexpression(startEndSubExpression)
            .range("9")
    )

    testErrorConditition(
        "n",
        "n"
    ) {
        SuperExpressive()
            .startOfInput()
            .atLeast(3).digit()
            .subexpression(startEndSubExpression) { ignoreStartAndEnd: false }
            .range("9")
    }

    testErrorConditition(
        "n",
        "n"
    ) {
        SuperExpressive()
            .endOfInput()
            .subexpression(startEndSubExpression) { ignoreStartAndEnd: false }
    }
    val namedCaptureSubExpression = SuperExpressive()
        .namedCapture("e")
        .exactly(2).anyChar()
        .end()
        .namedBackreference("e")

    testRegexEquality(
        "g",
        "/\\d{3,}(?<module>.{2})\\k<module>[0-9]/",
        SuperExpressive()
            .atLeast(3).digit()
            .subexpression(namedCaptureSubExpression)
            .range("9")
    )

    testRegexEquality(
        "g",
        "/\\d{3,}(?<yolomodule>.{2})\\k<yolomodule>[0-9]/",
        SuperExpressive()
            .atLeast(3).digit()
            .subexpression(namedCaptureSubExpression) { "namespace": "o" }
            .range("9")
    )

    testErrorConditition(
        ")",
        "p"
    ) {
        SuperExpressive()
            .namedCapture("e")
            .atLeast(3).digit()
            .end()
            .subexpression(namedCaptureSubExpression)
            .range("9")
    }

    testErrorConditition(
        ")",
        "p"
    ) {
        SuperExpressive()
            .namedCapture("e")
            .atLeast(3).digit()
            .end()
            .subexpression(namedCaptureSubExpression) { "namespace": "o" }
            .range("9")
    }
    val indexedBackreferenceSubexpression = SuperExpressive()
        .capture()
        .exactly(2).anyChar()
        .end()
        .backreference(1)

    testRegexEquality(
        "g",
        "/(\\d{3,})(.{2})\\2\\1[0-9]/",
        SuperExpressive()
            .capture()
            .atLeast(3).digit()
            .end()
            .subexpression(indexedBackreferenceSubexpression)
            .backreference(1)
            .range("9")
    )
    val nestedSubexpression = SuperExpressive().exactly(2).anyChar
    val firstLayerSubexpression = SuperExpressive()
        .string("n")
        .namedCapture("n")
        .optional.subexpression(nestedSubexpression)
        .end()
        .string("d")

    testRegexEquality(
        "s",
        "/(\\d{3,})outer begin(?<innerSubExpression>(?:.{2})?)outer end\\1[0-9]/",
        SuperExpressive()
            .capture()
            .atLeast(3).digit
            .end()
            .subexpression(firstLayerSubexpression)
            .backreference(1)
            .range("9")
    )
})