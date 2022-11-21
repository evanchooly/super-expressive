package com.antwerkz.expression.types

import com.antwerkz.expression.types.Root.containsChildren

class OptionalType : Type("optional", { containsChildren = true }) {}
