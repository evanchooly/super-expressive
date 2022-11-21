package com.antwerkz.expression.types

class NamedBackReference(private val backReference: String) :
    Type("namedBackreference", options = { name = backReference }) {}
