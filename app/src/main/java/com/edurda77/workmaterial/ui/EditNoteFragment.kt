package com.edurda77.workmaterial.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.model.DailyImageViewModel
import com.edurda77.workmaterial.model.ModelNote

class EditNoteFragment : Fragment() {
    private val viewModel by viewModels<DailyImageViewModel>()

    private lateinit var  titleEditText: EditText
    private lateinit var  contentEditText: EditText
    private lateinit var  saveButtom: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleEditText = view.findViewById(R.id.title_note_update)
        contentEditText = view.findViewById(R.id.content_note_update)
        saveButtom = view.findViewById(R.id.update_note)

        setFragmentResultListener("requestKey") { key, bundle ->
            val result = bundle.get("bundleKey") as ModelNote

            titleEditText.setText(result.titleNote)
            contentEditText.setText(result.contentNote)
            val note = ModelNote(result.idNote,result.titleNote, result.contentNote)
                viewModel.updateService(note,saveButtom, view.context)
            //viewModel.deleteService(note.idNote,saveButtom, view.context)
        }


    }
}