package org.example

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

fun main(args: Array<String>) {
    val j1 = JsonObject(mapOf(
        "test1" to JsonPrimitive("test1"),
        "test2" to JsonPrimitive("test2"),
    ))
    val j2 = listOf(
        JsonObject(mapOf(
        "test2" to JsonPrimitive("test2"),)
        ),
        JsonObject(mapOf(
        "test3" to JsonPrimitive("test2"),)
        ),
    )
    var merged = JsonObject(emptyMap())
    j1.forEach { key,value  -> merged = JsonObject(merged.plus(Pair(key, value)))  }
    j2.forEach { it.forEach { key,value  -> merged = JsonObject(merged.plus(Pair(key, value)))  }}
    println(merged)

}

