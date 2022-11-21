package com.antwerkz.expression.types

class AtLeast(private val count: Int) :
    Type(
        "atLeast",
        options = {
            times = listOf(count)
            containsChildren = true
        }
    ) {}
