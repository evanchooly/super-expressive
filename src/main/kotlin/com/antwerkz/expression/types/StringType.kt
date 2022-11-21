package com.antwerkz.expression.types

class StringType(value: String) : Type("string", value, { quantifierRequiresGroup = true }) {}
