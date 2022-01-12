package com.edurda77.workmaterial.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.model.DailyImageViewModel


class AddNoteFragment : Fragment() {
    private val viewModel by viewModels<DailyImageViewModel>()
    private lateinit var  titleEditText: EditText
    private lateinit var  contentEditText: EditText
    private lateinit var  saveButtom: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
        saveButtom = view.findViewById(R.id.save_note)
        viewModel.addService(titleEditText,contentEditText,saveButtom, view.context)

    }
}