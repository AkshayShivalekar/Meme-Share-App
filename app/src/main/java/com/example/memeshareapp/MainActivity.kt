package com.example.memeshareapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()
    }

    private fun loadMeme(){
        // Instantiate the RequestQueue.
        progressBar.visibility = View.VISIBLE
        currentImageUrl = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, currentImageUrl, null,
            Response.Listener { response ->
                val url = response.getString("url")
                progressBar.visibility = View.GONE
                Glide.with(this).load(url).into(memeImageView)
            },
            Response.ErrorListener { })

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }


    fun shareMeme(view: View) {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hey check what I have shared $currentImageUrl")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

//        val intent = Intent(Intent.ACTION_SEND)
//        intent.type = ("text/plain")
//        intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out what I have shared $currentImageUrl")
//        val chooser = Intent.createChooser(intent, "Share using...")
//        startActivity(chooser)
    }


    fun nextMeme(view: View) {
        loadMeme()
    }
}