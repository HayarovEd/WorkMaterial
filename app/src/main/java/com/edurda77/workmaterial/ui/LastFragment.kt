package com.edurda77.workmaterial.ui

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import coil.api.load
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.model.DailyImage
import com.edurda77.workmaterial.model.DailyImageViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputLayout

class LastFragment : Fragment() {

    private val viewModel by viewModels<DailyImageViewModel>()
    private lateinit var dailyImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var explanationTextView: TextView
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_last_image_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setDayAgo(view)
        setBottomAppBar(view)
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

    private fun initView() {
        dailyImageView = view!!.findViewById(R.id.image_last_view)
        setBottomSheetBehavior(view!!.findViewById(R.id.bottom_sheet_container))
        titleTextView = view!!.findViewById(R.id.sheet_peek)
        explanationTextView = view!!.findViewById(R.id.sheet_content)
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }

    private fun renderData(dailyImage: DailyImage) {

        when (dailyImage) {
            is DailyImage.Success -> {
                val serverResponseData = dailyImage.serverResponseData
                val url = serverResponseData.url
                titleTextView.text = serverResponseData.title
                explanationTextView.text = serverResponseData.explanation
                if (url.isEmpty()) {
                    Toast.makeText(context, "Фото отсутствует!", Toast.LENGTH_LONG).show()
                } else {
                    dailyImageView.load(url) {
                        lifecycle(this@LastFragment)
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



    private fun setDayAgo(view: View) {
        val lastDayTextView: TextView = view.findViewById(R.id.input_edit_last_data)
        val inputLayout: TextInputLayout = view.findViewById(R.id.input_layout_last_day)
        inputLayout.setEndIconOnClickListener {
            viewModel.getImageData(lastDayTextView.text.toString()
                .toInt()).observe(this, { dailyImage -> renderData(dailyImage) })
        }


    }
}