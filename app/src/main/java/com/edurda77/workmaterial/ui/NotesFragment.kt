package com.edurda77.workmaterial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.*
import androidx.recyclerview.widget.RecyclerView
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.model.DailyImageViewModel


class NotesFragment : Fragment() {
    private val viewModel by viewModels<DailyImageViewModel>()
    private lateinit var addNote: Button
    private lateinit var recyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNote = view.findViewById(R.id.add_note)
        recyclerView = view.findViewById(R.id.recycler_view_notes)
        addNote.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                ?.setReorderingAllowed(true)
                ?.replace(R.id.fragment_container_view, AddNoteFragment())
                ?.commit()
        }
        viewModel.setRecycledView(recyclerView,view.context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_notes, container, false)

    }



}