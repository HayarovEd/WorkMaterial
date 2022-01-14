package com.edurda77.workmaterial.model

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(
    private val oldList: List<ModelNote>,
    private val newList: List<ModelNote>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: ModelNote = oldList[oldItemPosition]
        val newItem: ModelNote = newList[newItemPosition]
        return oldItem.idNote == newItem.idNote
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: ModelNote = oldList[oldItemPosition]
        val newItem: ModelNote = newList[newItemPosition]
        return oldItem.titleNote == newItem.titleNote && oldItem.contentNote == newItem.contentNote
    }
}