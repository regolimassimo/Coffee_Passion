package com.massimoregoli.coffeepassion.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Parcellable per essere inserito nel Bundle
// Inserire     id 'kotlin-parcelize' in build.gradle module
@Parcelize
class Order(var type: Int,
                 var temp: Int,
                 var size: Int,
                 var addon: Int,
                 var sugar: Int,
                 var correction: Int) : Parcelable{}