package com.antwerkz.expression

import kotlin.text.RegexOption.DOT_MATCHES_ALL
import kotlin.text.RegexOption.IGNORE_CASE
import kotlin.text.RegexOption.MULTILINE
import kotlin.text.RegexOption.UNIX_LINES

data class Flags(
    var d: Boolean = false,
    var i: Boolean = false,
    var m: Boolean = false,
    var u: Boolean = false,
) {
    constructor(flags: Flags) : this(flags.d, flags.i, flags.m, flags.u)

    private fun Boolean.isTrue(flag: String): String {
        return if (this) flag else ""
    }

    override fun toString(): String {
        return d.isTrue("d") +
            i.isTrue("i") +
            m.isTrue("m") +
            u.isTrue("u")
    }

    fun toRegexOptions(): Set<RegexOption> {
        val options = mutableSetOf<RegexOption>()

        if (d) options += DOT_MATCHES_ALL
        if (i) options += IGNORE_CASE
        if (m) options += MULTILINE
        if (u) options += UNIX_LINES

        return options
    }
}
