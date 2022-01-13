package com.edurda77.workmaterial.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edurda77.blocknote2021.ui.NoteHolder
import com.edurda77.workmaterial.domain.ItemTouchHelperAdapter
import com.edurda77.workmaterial.model.ModelNote
import com.edurda77.workmaterial.model.RoomService
import java.util.*

class NoteAdapter(private val list: MutableList<ModelNote>, private val onClickListener: OnStateClickListener) :
    RecyclerView.Adapter<NoteHolder>(), ItemTouchHelperAdapter {
    interface OnStateClickListener {
        fun onStateClick(note: ModelNote, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {


        val note: ModelNote = list[position]
        holder.bind(note)

        holder.itemView.setOnClickListener {
            onClickListener.onStateClick(note, position)
        }
    }

    override fun getItemCount(): Int = list.size
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(list, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(list, i, i - 1)
            }
        }

        notifyItemMoved(fromPosition, toPosition)

    }

    override fun onItemDismiss(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
}