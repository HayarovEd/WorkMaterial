package com.edurda77.workmaterial.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import coil.api.load
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.model.DailyImage
import com.edurda77.workmaterial.model.DailyImageViewModel


class MarsFragment : Fragment() {

    private lateinit var bodySpaceImageView: ImageView
    private val viewModel by viewModels<DailyImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*viewModel.getMarsImageToday()
        val serverResponseData = viewModel.getMarsImageToday()[0]
        val url = serverResponseData.imgSrc
        bodySpaceImageView.load(url) {
            lifecycle(this@MarsFragment)
            error(R.drawable.ic_image_error)
            placeholder(R.drawable.ic_no_photo)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_mars, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)

    }






    private fun initView(view:View) {
        bodySpaceImageView = view.findViewById(R.id.image_mars)


    }


}