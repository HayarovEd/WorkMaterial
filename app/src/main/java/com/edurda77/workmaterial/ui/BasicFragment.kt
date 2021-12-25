package com.edurda77.workmaterial.ui

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.api.load
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.model.DailyImage
import com.edurda77.workmaterial.model.DailyImageViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputLayout
import android.view.LayoutInflater as LayoutInflater1
import androidx.annotation.NonNull
import com.google.android.material.navigation.NavigationView


class BasicFragment : Fragment() {


    private val viewModel by viewModels<DailyImageViewModel>()

    private lateinit var dailyImageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel.getImageData(0).observe(this, { dailyImage -> renderData(dailyImage) })
    }

    override fun onCreateView(
        inflater: LayoutInflater1,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dailyImageView = view.findViewById(R.id.image_view)

        setTextInput(view, savedInstanceState)


    }

    private fun renderData(dailyImage: DailyImage) {

        when (dailyImage) {
            is DailyImage.Success -> {
                val serverResponseData = dailyImage.serverResponseData
                val url = serverResponseData.url

                if (url.isEmpty()) {
                    Toast.makeText(context, "Сегодня фото отсутствует!", Toast.LENGTH_LONG).show()
                } else {
                    dailyImageView.load(url) {
                        lifecycle(this@BasicFragment)
                        error(R.drawable.ic_image_error)
                        placeholder(R.drawable.ic_no_photo)
                    }
                }
            }
            is DailyImage.Loading -> {

            }
            is DailyImage.Error -> {
                Toast.makeText(context, "Фото не загружено", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun setTextInput(view: View, savedInstanceState: Bundle?) {
        val wikiTextView: TextView = view.findViewById(R.id.input_edit_text_wiki)
        val inputLayout: TextInputLayout = view.findViewById(R.id.input_layout_wiki)
        viewModel.searchWiki(wikiTextView, inputLayout, view.context, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }





}
