package com.digiart.ui.sell.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.digiart.R
import com.digiart.ui.sell.Listing
import com.google.android.material.snackbar.Snackbar

class Secondscreen : Fragment() {

    lateinit var listingItem: Listing
    private lateinit var rootView: View
    private var listingItemMedia:String? = null
    private var artCategory: String? = null
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var pickContentLauncher: ActivityResultLauncher<String>
    private var uploadedAssetsList: MutableList<String> = mutableListOf()
    val selectedCategory = arguments?.getString("selectedCategory")
    private val digitalArtMediaTypes = arrayOf(
        "image/*",       // Digital Illustrations
        "image/*",       // Digital Painting
        "image/svg+xml", // Vector Art
        "model/*",       // 3D Art
        "image/gif",     // Pixel Art
        "image/*",       // Digital Collage
        "image/*",       // Digital Photography
        "image/*",       // Digital Comics
        "image/*",       // NFT Art
        "image/*",       // Generative Art
        "video/*",       // Motion Graphics and Animation
        "image/*"        // Digital Calligraphy and Lettering
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_secondscreen, container, false)

        // Retrieve the selected category from Firstscreen companion object
        val selectedCategory = arguments?.getString("selectedCategory")
        // Initialize the pickContentLauncher
        pickContentLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                // Handle the selected media asset
                handleMediaAssetSelection(it)
            }
        }
        // Find the next button
        val backBtn = rootView.findViewById<Button>(R.id.previousBtn)
        // Find the upload button

        // Set click listener for the back button
        backBtn.setOnClickListener {
            // Navigate back to the previous fragment
            val firstFragment = Firstscreen()
            // Get the FragmentManager
            val fragmentManager = requireActivity().supportFragmentManager

            // Begin a FragmentTransaction
            fragmentManager.beginTransaction().apply {
                // Replace the current fragment with the SecondScreen fragment
                replace(R.id.SellSecondScreen, firstFragment)
                // Add the transaction to the back stack (optional)
                addToBackStack(null)
                // Commit the transaction
                commit()
            }
        }
        val uploadBtn = rootView.findViewById<Button>(R.id.uploadListingBtn)
        // Set click listener for the upload button
        uploadBtn.setOnClickListener {
            // Check if a category was selected in the previous fragment
            if (selectedCategory != null) {
                // Check if the uploaded media asset matches the selected category
                    // Upload the media asset
                    pickMediaAsset()

            } else {
                Toast.makeText(context, "No category selected", Toast.LENGTH_SHORT).show()
            }
        }
        val nextBtn = rootView.findViewById<Button>(R.id.nextBtn)
        val title = rootView.findViewById<EditText>(R.id.listingNameEditText)
        val description = rootView.findViewById<EditText>(R.id.listingDescEditText)
        val tags = rootView.findViewById<EditText>(R.id.listingTagEditText)
        val priceText = rootView.findViewById<EditText>(R.id.listingPriceEditText)
        nextBtn.setOnClickListener {

            try{
                // Validate input fields
                if (title.text.length == 0 || description.text.length == 0 || priceText.text.length == 0 || tags.text.length == 0) {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener // Exit the function early if any field is empty
                }

                val price = priceText.text.toString().toDoubleOrNull() // Use toDoubleOrNull to safely convert input to Double
                if (price == null || price <= 0) {
                    Toast.makeText(context, "Please enter a valid price", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener // Exit the function if price is null or negative
                }

                // Pass necessary parameters to create a new Listing instance
                listingItem = Listing(
                    title = title.text.toString(),
                    tags = tags.text.toString(),
                    category = selectedCategory.toString(),
                    description = description.text.toString(),
                    price = price,
                    listing_media = listingItemMedia.toString() // Convert URI to string and store
                )
                // Show a green snackbar success message
                Snackbar.make(rootView, "Listing details captured successfully", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(android.R.color.holo_green_dark))
                    .show()
            } catch (e: Exception) {
                // Show a red snackbar error message with the error details
                Snackbar.make(rootView, "Error: ${e.message}", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(resources.getColor(android.R.color.holo_red_dark))
                    .show()
            }
            val nextBtn = rootView.findViewById<Button>(R.id.nextBtn)

            nextBtn.setOnClickListener {
                // Navigate to the ThirdscreenFragment using NavController
                val bundle = Bundle().apply {
                    putParcelable("listingItem", listingItem)
                }
                val thirdFragment = Thirdscreen().apply {
                    arguments = bundle
                }
                val secondFragment = Secondscreen()
                // Get the FragmentManager
                val fragmentManager = requireActivity().supportFragmentManager

                // Begin a FragmentTransaction
                fragmentManager.beginTransaction().apply {
                    // Replace the current fragment with the Thirdscreen fragment
                    replace(R.id.SellSecondScreen, thirdFragment)
                    // Add the transaction to the back stack (optional)
                    addToBackStack(null)
                    // Commit the transaction
                    commit()
                }
            }


        }

        return rootView
    }
    // Function to get the supported media type based on the selected art category
    private fun getSupportedMediaType(selectedArtCategory: String): String {
        return when (selectedArtCategory) {
            "Digital Illustration" -> digitalArtMediaTypes[0]
            "Digital Painting" -> digitalArtMediaTypes[1]
            "Vector Art" -> digitalArtMediaTypes[2]
            "3D Art" -> digitalArtMediaTypes[3]
            "Pixel Art" -> digitalArtMediaTypes[4]
            "Digital Collage" -> digitalArtMediaTypes[5]
            "Digital Photograph" -> digitalArtMediaTypes[6]
            "Digital Comic" -> digitalArtMediaTypes[7]
            "NFT Art" -> digitalArtMediaTypes[8]
            "Generative Art" -> digitalArtMediaTypes[9]
            "Motion Graphics and Animation" -> digitalArtMediaTypes[10]
            "Digital Calligraphy and Lettering" -> digitalArtMediaTypes[11]
            else -> ""
        }
    }
    // Function to launch the system picker for selecting a media asset
    // Modify pickMediaAsset function to use appropriate MIME type
    private fun pickMediaAsset() {
        // Retrieve the selected art category from Firstscreen
        val selectedCategory = arguments?.getString("selectedCategory")

        // Check if the selected category is not null
        if (selectedCategory != null) {
            // Get the supported media type for the selected art category
            val supportedMediaType = getSupportedMediaType(selectedCategory)

            // Check for permission to access the device's media
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is granted, launch the content picker with the supported media type
                if (supportedMediaType != null) {
                    pickContentLauncher.launch(supportedMediaType)
                } else {
                    // Unsupported media type, show an error message
                    Toast.makeText(
                        context,
                        "Unsupported media type",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                // If permission is not granted, request permission
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            // If no category is selected, show a message
            Toast.makeText(context, "No category selected", Toast.LENGTH_SHORT).show()
        }
    }
    private fun handleMediaAssetSelection(mediaAssetUri: Uri) {
        // Store the media asset URI in the Listing item
        listingItemMedia = mediaAssetUri.toString() // Convert URI to string and store

        if (mediaAssetUri.toString()!=null) {
            // Show a green snackbar success message
            Snackbar.make(rootView, "Media asset uploaded successfully", Snackbar.LENGTH_LONG)
                .setBackgroundTint(resources.getColor(android.R.color.holo_green_dark))
                .show()
        } else {
            // Show a red snackbar error message
            Snackbar.make(rootView, "Failed to upload media asset", Snackbar.LENGTH_LONG)
                .setBackgroundTint(resources.getColor(android.R.color.holo_red_dark))
                .show()
        }
    }


}