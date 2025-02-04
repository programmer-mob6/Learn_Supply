package com.example.learnsupply.util

import com.tagsamurai.tscomponents.button.OptionData

object DataDummy {
    fun generateOptionDataString(list: List<String>): List<OptionData<String>> {
        return list.map { OptionData(it, it) }
    }

//    inline fun <reified T> toJsonIfNotEmpty(value: List<T>): String? {
//        return if (value.isNotEmpty()) Gson().toJson(value) else null
//    }
}
