package org.enum

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class EnumTest (name: String, name2: String){
    TEST("test", "test")
}