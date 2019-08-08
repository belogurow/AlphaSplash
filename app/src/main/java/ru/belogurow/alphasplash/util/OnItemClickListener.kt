package ru.belogurow.alphasplash.util

import android.view.View

interface OnItemClickListener<T> {
    fun onItemClick(view: View, item: T)
}
