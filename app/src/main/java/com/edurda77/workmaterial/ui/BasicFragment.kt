package com.edurda77.workmaterial.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*

import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.fragment.app.viewModels
import coil.api.load
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.model.DailyImage
import com.edurda77.workmaterial.model.DailyImageViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputLayout
import android.view.LayoutInflater as LayoutInflater1

class BasicFragment : Fragment() {


    private val viewModel by viewModels<DailyImageViewModel>()

    private lateinit var dailyImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var explanationTextView: TextView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel.getImageData().observe(this, { dailyImage -> renderData(dailyImage) })
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
        initView(view)
        setTextInput(view, savedInstanceState)
        setBottomAppBar(view)
    }

    private fun renderData(dailyImage: DailyImage) {

        when (dailyImage) {
            is DailyImage.Success -> {
                val serverResponseData = dailyImage.serverResponseData
                val url = serverResponseData.url
                titleTextView.text = serverResponseData.title
                explanationTextView.text = serverResponseData.explanation
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

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> Toast.makeText(context, "Избранное", Toast.LENGTH_SHORT).show()
            R.id.action_search -> Toast.makeText(context, "Поиск фото", Toast.LENGTH_SHORT).show()
            android.R.id.home -> {
                val activity = requireActivity()
                BottomNavigationDrawerFragment().show(activity.supportFragmentManager, "tag")
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = requireContext() as AppCompatActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
    }

    private fun initView(view: View) {
        dailyImageView = view.findViewById(R.id.image_view)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        titleTextView = view.findViewById(R.id.sheet_peek)
        explanationTextView = view.findViewById(R.id.sheet_content)
    }


}
