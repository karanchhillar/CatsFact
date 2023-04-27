package com.example.catsfact

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var data : JSONArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadfact()
    }

    @SuppressLint("SetTextI18n")
    private fun loadfact(){
        val queue = Volley.newRequestQueue(this)
        val random = Random.nextInt(1,35)
        val url = "https://catfact.ninja/facts?page=$random"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                data = response.getJSONArray("data")

                val randomNumber = Random.nextInt(data!!.length())
                val jsonObject = data!!.getJSONObject(randomNumber)
                val fact = jsonObject.getString("fact")
                val length = jsonObject.getInt("length")


                val factView : TextView = findViewById(R.id.fact_text)
                factView.text = fact

                val lengthView : TextView = findViewById(R.id.length_text)
                lengthView.text = "Length = ${length.toString()}"

            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()

            })

        queue.add(jsonObjectRequest)
    }
    fun nextFact(view: View) {
        loadfact()
    }
}