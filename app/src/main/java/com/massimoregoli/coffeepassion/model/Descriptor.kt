package com.massimoregoli.coffeepassion.model

import android.content.Context
import com.massimoregoli.coffeepassion.R

class Descriptor(private var context: Context) {
    val TYPE: Array<String> =
        context.resources.getStringArray(R.array.type)
    val CORRECTION: Array<String> =
        context.resources.getStringArray(R.array.correction)
    val SUGAR: Array<String> =
        context.resources.getStringArray(R.array.sugar)
    val ADDON: Array<String> =
        context.resources.getStringArray(R.array.addon)
    val SIZE: Array<String> =
        context.resources.getStringArray(R.array.size)
    val TEMP: Array<String> =
        context.resources.getStringArray(R.array.temp)

    fun describe(o: Order): String {
        val type = TYPE[o.type]
        val cold = TEMP[o.temp]
        val corr = CORRECTION[o.correction]
        val addon = ADDON[o.addon]
        val sugar = if (o.sugar > 0)
            context.getString(R.string.sugar).format(o.sugar) else context.getString(R.string.bitter)
        val size = SIZE[o.size]
        val elem = listOf(type, cold, size, addon, corr, sugar)
        var ret = context.getString(R.string.coffee)
        for (e in elem)
            if (e != "")
                ret += (" $e,")
        return ret
    }
}