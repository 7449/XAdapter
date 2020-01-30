package com.xadapter.recyclerview

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.fixedSize() = also { setHasFixedSize(true) }