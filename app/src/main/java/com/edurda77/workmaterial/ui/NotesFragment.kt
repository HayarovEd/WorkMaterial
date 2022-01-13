package com.edurda77.workmaterial.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
                    //Thread {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .setCustomAnimations(
                            R.animator.slide_in_left,
                            R.animator.slide_in_right
                        )
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, AddNoteFragment())
                        .commit()
                    // }.start()
                }
            }

        return@lazy NoteAdapter(stateClickListener)
    }


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
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

//    fun initRecycledView(recyclerView: RecyclerView, context: Context, fragment: Fragment) {
//
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        val notes = initNotes(context)
//        val stateClickListener: NoteAdapter.OnStateClickListener =
//            object : NoteAdapter.OnStateClickListener {
//                override fun onStateClick(note: ModelNote, position: Int) {
//                    Thread {
//                        fragment.requireActivity().supportFragmentManager
//                            .beginTransaction()
//                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                            .setCustomAnimations(
//                                R.animator.slide_in_left,
//                                R.animator.slide_in_right
//                            )
//                            .setReorderingAllowed(true)
//                            .replace(R.id.fragment_container_view, AddNoteFragment())
//                            .commit()
//                    }.start()
//                }
//            }
//        recyclerView.adapter = NoteAdapter(notes as MutableList<ModelNote>, stateClickListener)
//        val callback = SimpleItemTouchHelperCallback(recyclerView.adapter as NoteAdapter)
//        val touchHelper = ItemTouchHelper(callback)
//        touchHelper.attachToRecyclerView(recyclerView)
//        val sampleDiffUtil = DiffUtilCallback(
//            currentNotes,
//            notes
//        )
//        val sampleDiffResult = DiffUtil.calculateDiff(sampleDiffUtil)
//        currentNotes = notes
//        sampleDiffResult.dispatchUpdatesTo(recyclerView.adapter as NoteAdapter)
//        //recyclerView.adapter = NoteAdapter(nots, stateClickListener)
//    }

//    private fun initNotes(context: Context): List<ModelNote> {
//        val roomService = RoomService(context)
//        Thread {
//            roomService.getNotes().forEach {
//                currentNotes.add(it)
//            }
//        }.start()
//
//        return currentNotes
//    }


}