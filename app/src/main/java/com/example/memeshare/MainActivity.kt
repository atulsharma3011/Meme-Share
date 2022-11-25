package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
var impurl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
        pbar.visibility= View.VISIBLE
    }


    private fun loadmeme() {
        pbar.visibility= View.VISIBLE
  //  val queue = Volley.newRequestQueue(this)
    val url = "https://meme-api.herokuapp.com/gimme"


    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url,null,

        Response.Listener { response ->
             impurl =response.getString("url")
            Glide.with(this).load(impurl).listener(object :RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    pbar.visibility=View.GONE
                    return false

                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    pbar.visibility=View.GONE
                    return false

                }
            }

            ).into(imageView)



        },
        Response.ErrorListener{
           Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()
        })

// Add the request to the RequestQueue.

   // queue.add(jsonObjectRequest)
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
}
    fun sharememe(view: android.view.View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"

        intent.putExtra(Intent.EXTRA_TEXT,"$impurl")
        val chooser=Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
    fun nextmeme(view: android.view.View) {
        loadmeme()
    }
}