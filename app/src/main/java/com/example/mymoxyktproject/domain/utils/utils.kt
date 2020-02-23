package com.example.mymoxyktproject.domain.utils

import com.example.mymoxyktproject.R

fun weatherCodeToImage(weatherCode: Int): Int = when (weatherCode) {
    113 -> R.drawable.ic_clear
    260, 248, 116, 143, 122, 119 -> R.drawable.ic_cloudy
    296, 293, 284, 281, 266, 263, 176, 299, 302, 305, 308, 311, 314, 359 -> R.drawable.ic_drizzle
    326, 179, 182, 185 -> R.drawable.ic_less_snowy
    395, 368, 371, 374, 377 -> R.drawable.ic_snowy
    200 -> R.drawable.ic_thunder
    230, 227, 317, 320, 323, 329, 332, 335, 338, 350 -> R.drawable.ic_snowy
    365, 362, 356 -> R.drawable.ic_rainy
    386, 389, 392 -> R.drawable.ic_thunder_drizzle
    else -> R.drawable.ic_null
}
