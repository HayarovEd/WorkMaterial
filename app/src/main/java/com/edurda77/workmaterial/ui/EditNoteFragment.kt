package com.edurda77.workmaterial.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.model.DailyImageViewModel

class EditNoteFragment : Fragment() {
    private val viewModel by viewModels<DailyImageViewModel>()

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button

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
        saveButton = view.findViewById(R.id.update_note)
        deleteButton = view.findViewById(R.id.delete_note)

        setFragmentResultListener("requestKey") { key, bundle ->
            val result = bundle.get("bundleKey") as Int
            viewModel.getNote(requireContext(), result).observe(viewLifecycleOwner) { item ->
                val id = item.idNote
                titleEditText.setText(item.titleNote)
                contentEditText.setText(item.contentNote)
                viewModel.updateService(
                    id,
                    titleEditText,
                    contentEditText,
                    saveButton,
                    view.context
                )
                viewModel.deleteService(id, deleteButton, view.context)
            }
        }
    }
}