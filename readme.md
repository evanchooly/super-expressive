# Super Expressive

![Super Expressive Logo](./logo.png)

**Super Expressive** is a Kotlin library that allows you to build regular expressions in almost natural language - with no extra 
dependencies, and a lightweight code footprint.  It is a Kotlin/JVM port of the javascript library [super-expressive](https://github.com/francisrstokes/super-expressive#super-expressive).  

It has been lightly modified to make sense with Java's regular expressions and the API tweaked to make for more idiomatic Kotlin usage.

---

- [Why](#Why)
- [Installation and Usage](#Installation-and-Usage)

## Why?

Regex is a very powerful tool, but its terse and cryptic vocabulary can make constructing and communicating them with others a challenge. Even developers who understand them well can have trouble reading their own back just a few months later! In addition, they can't be easily created and manipulated in a programmatic way - closing off an entire avenue of dynamic text processing.

That's where **Super Expressive** comes in. It provides a programmatic and human-readable way to create regular expressions. Its API uses the [fluent builder pattern](https://en.wikipedia.org/wiki/Fluent_interface), and is completely immutable. It's built to be discoverable and predictable:

- properties and methods describe what they do in plain English
- e.g., rather than writing this regex:  `(?:hello|\\d|\\w|[\\.#])`, now you write it like this;
```kotlin
  anyOf { string("hello").digit().word().char('.').char('#') }
```
- order matters! quantifiers are specified before the thing they change, just like in English (e.g. `val regex = exactly(5).digit`)
- if you make a mistake, you'll know how to fix it. SuperExpressive will guide you towards a fix if your expression is invalid
- [subexpressions](#subexpressionexpr-opts) can be used to create meaningful, reusable components

SuperExpressive turns those complex and unwieldy regexes that appear in code reviews into something that can be read, understood, and **properly reviewed** by your peers - and maintained by anyone!

## Installation and Usage

```xml
<dependency>
    <groupId>com.antwerkz</groupId>
    <artifactId>super-expressive</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

There is really only one type to be concerned with:  `RegularExpression`.  In the dokka pages, all of the methods have short examples 
showing their usage and the regex values generated.  You'll note that each of these examples start off with what appears to be function 
calls with no type associated with them.  What's happening there is that those initial calls are issued against the `RegularExpression` 
companion object and this then bootstraps you in to a proper `RegularExpression` builder. 

## Again, WHY?

This project caught my eye a while back and it was intriguing.  I like writing DSLs in Kotlin and thought I'd give it a try.  I 
didn't/don't know typescript but learned a little bit when porting this.  Not quite enough of it as there are couple of dark corners 
that I would love to clean up but they break things so they're still slightly gnarly.

Will anyone use this library?  Will *I* use it?  I don't know.  The point was to have fun porting it.  Perhaps you will find it useful 
and if that's the case, I'll do my best to support you with whatever issues come up.  In the end, I'm rather pleased with most of the 
API changes I made to the javascript version (there are a couple left to play with) and some of the "clever" solutions I came up with to 
support them.  I had fun doing it and that was the goal.  So, to quote President George W. Bush, "MISSION ACCOMPLISHED."  If you 
like/use this, I would love to know so I can gauge how much mental energy to really making this a rock solid stable library.