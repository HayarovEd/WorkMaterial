package com.edurda77.workmaterial.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.model.*


class NotesFragment : Fragment() {

    private val viewModel by viewModels<DailyImageViewModel>()

    private lateinit var addNote: Button
    private lateinit var recyclerView: RecyclerView

    private val adapter by lazy {
        val stateClickListener: NoteAdapter.OnStateClickListener =
            object : NoteAdapter.OnStateClickListener {

                override fun onStateClick(note: ModelNote, position: Int) {
                    setFragmentResult("requestKey", bundleOf("bundleKey" to note))
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .setCustomAnimations(
                            R.animator.slide_in_left,
                            R.animator.slide_in_right
                        )
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, EditNoteFragment())
                        .commit()
                }
            }

        return@lazy NoteAdapter(stateClickListener)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNote = view.findViewById(R.id.add_note)
        recyclerView = view.findViewById(R.id.recycler_view_notes)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        addNote.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, AddNoteFragment())
                .commit()
        }

        viewModel.getNotes(requireContext()).observe(viewLifecycleOwner) { items ->
            adapter.list = items.toMutableList()

            val sampleDiffUtil = DiffUtilCallback(
                adapter.list,
                items,
            )

            val sampleDiffResult = DiffUtil.calculateDiff(sampleDiffUtil)
            adapter.list = items.toMutableList()

            sampleDiffResult.dispatchUpdatesTo(recyclerView.adapter as NoteAdapter)
            val callback = SimpleItemTouchHelperCallback(recyclerView.adapter as NoteAdapter)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(recyclerView)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

}