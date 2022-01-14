package com.edurda77.workmaterial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.model.DailyImageViewModel


class AddNoteFragment : Fragment() {
    private val viewModel by viewModels<DailyImageViewModel>()
    private lateinit var  titleEditText: EditText
    private lateinit var  contentEditText: EditText
    private lateinit var  saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleEditText = view.findViewById(R.id.title_note_add)
        contentEditText = view.findViewById(R.id.content_note_add)
        saveButton = view.findViewById(R.id.save_note)
        viewModel.addService(titleEditText,contentEditText,saveButton, view.context)

    }
}