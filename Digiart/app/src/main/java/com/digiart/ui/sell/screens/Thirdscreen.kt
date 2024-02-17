package com.digiart.ui.sell.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import com.digiart.R
import com.digiart.ui.sell.Listing
import org.json.JSONObject

class Thirdscreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_thirdscreen, container, false)
        // Retrieve Listing data from arguments bundle
        val listingItem = arguments?.getParcelable<Listing>("listingItem")

        // Access TextView elements
        val sellPreviewMediaAsset = rootView.findViewById<WebView>(R.id.sellPreviewMediaAsset)

        val sellPreviewListingName = rootView.findViewById<TextView>(R.id.sellPreviewListingName)
        val sellPreviewListingArtType = rootView.findViewById<TextView>(R.id.sellPreviewListingArtType)
        val sellPreviewListingDesc = rootView.findViewById<TextView>(R.id.sellPreviewListingDesc)
        val sellPreviewListingTags = rootView.findViewById<TextView>(R.id.sellPreviewListingTags)
        val sellPreviewListingPrice = rootView.findViewById<TextView>(R.id.sellPreviewListingPrice)

        // Load the media asset URL into the WebView
        listingItem?.listing_media?.let { mediaUrl ->
            sellPreviewMediaAsset.loadUrl(mediaUrl)
        }

        // Populate TextView elements with Listing data
        listingItem?.let {
            sellPreviewListingName.text = it.title
            sellPreviewListingArtType.text = it.category // Replace with appropriate field from Listing object
            sellPreviewListingDesc.text = it.description
            sellPreviewListingTags.text = it.tags
            sellPreviewListingPrice.text = it.price.toString()
        }

        return rootView

    }

}