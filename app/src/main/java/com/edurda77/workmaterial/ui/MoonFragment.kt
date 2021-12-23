package com.edurda77.workmaterial.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.edurda77.workmaterial.R


class MoonFragment : Fragment() {

    private lateinit var bodySpaceImageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_moon, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setBottomAppBar(view)
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.image_earth -> {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container_view, EarthFragment())
                    ?.commitAllowingStateLoss()

            }

            R.id.image_mars -> {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container_view, MarsFragment())
                    ?.commitAllowingStateLoss()

            }

            R.id.image_moon -> {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container_view, MoonFragment())
                    ?.commitAllowingStateLoss()

            }

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

    private fun initView(view:View) {
        bodySpaceImageView = view.findViewById(R.id.image_moon)


    }
}