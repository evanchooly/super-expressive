package com.antwerkz.expression.types

class Subexpression(private val list: List<Type>) :
    Type(
        "subexpression",
        list,
        {
            containsChildren = true
            quantifierRequiresGroup = true
        }
    ) {}
