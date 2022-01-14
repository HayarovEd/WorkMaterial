package com.edurda77.workmaterial.ui

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.api.load
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.model.DailyImageViewModel


class EarthFragment : Fragment() {
    private lateinit var bodySpaceImageView: ImageView
    private val viewModel by viewModels<DailyImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread {
            viewModel.getEarthImageToday()
            val serverResponseData = viewModel.getEarthImageToday()[0]
            val url = viewModel.getStringFromDate(1) + serverResponseData.image + ".jpg"
            bodySpaceImageView.load(url) {
                lifecycle(this@EarthFragment)
                error(R.drawable.ic_image_error)
                placeholder(R.drawable.ic_no_photo)
            }
        }.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)

    }


    private fun initView(view: View) {
        bodySpaceImageView = view.findViewById(R.id.image_earth)


    }
}