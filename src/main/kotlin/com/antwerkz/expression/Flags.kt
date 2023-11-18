package com.antwerkz.expression

import kotlin.text.RegexOption.CANON_EQ
import kotlin.text.RegexOption.COMMENTS
import kotlin.text.RegexOption.DOT_MATCHES_ALL
import kotlin.text.RegexOption.IGNORE_CASE
import kotlin.text.RegexOption.LITERAL
import kotlin.text.RegexOption.MULTILINE
import kotlin.text.RegexOption.UNIX_LINES

internal data class Flags(val options: MutableSet<RegexOption> = mutableSetOf()) {
    fun ignoreCase() {
        options += IGNORE_CASE
    }

    fun multiLine() {
        options += MULTILINE
    }

    fun canonicalEquivalance() {
        options += CANON_EQ
    }

    fun allowComments() {
        options += COMMENTS
    }

    fun dotAll() {
        options += DOT_MATCHES_ALL
    }

    fun literal() {
        options += LITERAL
    }

    fun unixLines() {
        options += UNIX_LINES
    }
}
