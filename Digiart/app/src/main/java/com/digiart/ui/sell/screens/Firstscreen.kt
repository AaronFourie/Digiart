package com.digiart.ui.sell.screens

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.digiart.R
import com.google.android.material.button.MaterialButton

class Firstscreen : Fragment() {

    companion object {
        var selectedCategory: String? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()

        // Hide the status bar
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

        val view = inflater.inflate(R.layout.fragment_firstscreen, container, false)

        // Find all the MaterialButton views
        val buttons = arrayOf(
            view.findViewById<MaterialButton>(R.id.digitalIllustrationsButton),
            view.findViewById<MaterialButton>(R.id.digitalIlPaintingButton),
            view.findViewById<MaterialButton>(R.id.vectorArtButton),
            view.findViewById<MaterialButton>(R.id.threeDArtButton),
            view.findViewById<MaterialButton>(R.id.pixelArtButton),
            view.findViewById<MaterialButton>(R.id.digitalCollageButton),
            view.findViewById<MaterialButton>(R.id.digitalPhotographyButton),
            view.findViewById<MaterialButton>(R.id.digitalComicsButton),
            view.findViewById<MaterialButton>(R.id.nftArtButton),
            view.findViewById<MaterialButton>(R.id.generativeArtButton),
            view.findViewById<MaterialButton>(R.id.motionGrahpicsAndAnimationButton),
            view.findViewById<MaterialButton>(R.id.digitalCalligraphyAndLetteringButton)
        )
        val artTypes = arrayOf(
            view.findViewById<TextView>(R.id.DigitalIllustationLbl),
            view.findViewById<TextView>(R.id.DigitalPaintingLbl),
            view.findViewById<TextView>(R.id.VectorArtLbl),
            view.findViewById<TextView>(R.id.ThreeDArtLbl),
            view.findViewById<TextView>(R.id.PixelArtLbl),
            view.findViewById<TextView>(R.id.DigitalCollageLbl),
            view.findViewById<TextView>(R.id.DigitalPhotographLbl),
            view.findViewById<TextView>(R.id.DigitalComicLbl),
            view.findViewById<TextView>(R.id.NFTArtLbl),
            view.findViewById<TextView>(R.id.GenerativeArtLbl),
            view.findViewById<TextView>(R.id.MotionGraphicsAndAnimationLbl),
            view.findViewById<TextView>(R.id.DigitalCalligraphyAndLettingLbl)
        )

        var lastSelectedButton: MaterialButton? = null
        var lastSelectedCategory: String? = null

        // Find the next button
        val nextBtn = view.findViewById<Button>(R.id.nextBtn)

        // Set click listeners to handle button selection
        for (index in buttons.indices) {
            buttons[index].setOnClickListener {
                // Check if there was a previously selected button
                lastSelectedButton?.apply {
                    // Change back to light gray background and black icon
                    setBackgroundColor(resources.getColor(R.color.light_gray))
                    setIconTintResource(android.R.color.black)
                }
                // Set current button to selected state (dark gray background, white icon)
                buttons[index].apply {
                    setBackgroundColor(resources.getColor(R.color.dark_gray))
                    setIconTintResource(android.R.color.white)
                }
                // Update the last selected button and category
                lastSelectedButton = buttons[index]
                lastSelectedCategory = artTypes[index].text.toString()

                // Enable or disable the next button based on the selection state
                nextBtn.isEnabled = true
            }
        }

        // Set click listener for the next button
        nextBtn.setOnClickListener {
            // Check if any button is selected
            if (lastSelectedButton != null) {
                selectedCategory = lastSelectedCategory;
                // Navigate to the SecondScreenFragment using NavController
                val bundle = Bundle().apply {
                    putString("selectedCategory", selectedCategory)
                }
                val secondFragment = Secondscreen().apply {
                    arguments = bundle
                }
                val firstFragment = Firstscreen()
                // Get the FragmentManager
                val fragmentManager = requireActivity().supportFragmentManager

                // Begin a FragmentTransaction
                fragmentManager.beginTransaction().apply {
                    // Replace the current fragment with the SecondScreen fragment
                    replace(R.id.SellFirstScreen, secondFragment)
                    // Add the transaction to the back stack (optional)
                    addToBackStack(null)
                    // Commit the transaction
                    commit()
                }
            } else {
                Toast.makeText(context, "Please select an art type", Toast.LENGTH_SHORT).show()
            }
        }

        // Disable the next button initially
        nextBtn.isEnabled = false

        return view
    }

}
