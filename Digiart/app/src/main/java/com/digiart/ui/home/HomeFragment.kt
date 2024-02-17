package com.digiart.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.imageview.ShapeableImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.digiart.R
import com.digiart.databinding.FragmentHomeBinding
import org.json.JSONArray
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var adapter: StaggeredGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)

        val apiKey = "42181235-896713bfdbdd7703b4d3edea4"
        val pixabayApiUrl =
            "https://pixabay.com/api/?key=$apiKey&q=digital+art&image_type=all&per_page=200"

        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, pixabayApiUrl, null,
            Response.Listener { response ->
                val imageUrls = parsePixabayResponse(response)
                adapter = StaggeredGridAdapter(requireContext(), imageUrls, requestQueue)
                recyclerView.adapter = adapter
            },
            Response.ErrorListener { error ->
                // Handle errors here
            })

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        requestQueue.add(jsonRequest)

        return rootView
    }

    private fun parsePixabayResponse(response: JSONObject): List<String> {
        val imageUrls = mutableListOf<String>()
        val hitsArray: JSONArray = response.getJSONArray("hits")

        for (i in 0 until hitsArray.length()) {
            val hitObject: JSONObject = hitsArray.getJSONObject(i)
            val imageUrl: String = hitObject.getString("webformatURL")
            imageUrls.add(imageUrl)
        }

        return imageUrls
    }
}