package com.antwerkz.expression.types

class Exactly(private val count: Int) :
    Type(
        "exactly",
        options = {
            times = listOf(count)
            containsChildren = true
        }
    ) {}
