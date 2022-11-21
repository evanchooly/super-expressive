package com.antwerkz.expression.types

class RangeType(start: Char, end: Char) : Type("range", listOf(start, end)) {}
