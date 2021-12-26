package com.edurda77.workmaterial.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import coil.api.load
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.model.DailyImageViewModel
import com.google.android.material.textfield.TextInputLayout


class PhotoFragment : Fragment() {

    private lateinit var bodySpaceImageView: ImageView
    private val viewModel by viewModels<DailyImageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextInput(view)
    }


    private fun setTextInput(view: View) {
        bodySpaceImageView = view.findViewById(R.id.image_search_photo)
        val searchTextView: TextView = view.findViewById(R.id.input_edit_text_search_internet)
        val inputLayout: TextInputLayout = view.findViewById(R.id.input_layout_search_photo)
        viewModel.searchPhotos(searchTextView,inputLayout,this@PhotoFragment,bodySpaceImageView,
            viewModel)

    }
}