package com.iotarch.babyroomtemperature

import android.os.AsyncTask
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var tempearture=findViewById<TextView>(R.id.temperature)

        var button = findViewById<Button>(R.id.butTemparture)

        button.setOnClickListener {

            var atask = ConnectInternet()
            atask.execute()


        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class ConnectInternet : AsyncTask<Void,Void,String>(){

        var connectionHttp = "https://us.wio.seeed.io/v1/node/GroveTempHumProD1/temperature?access_token=1bd3165e0b3fe2403dd5e032ce54f537"

        override fun doInBackground(vararg p0: Void?): String {
            var client = OkHttpClient()
            var request = Request.Builder().url(connectionHttp).build()
            var response=client.newCall(request).execute()

            var jsonObject = JSONObject(response.body?.string())

            var temp=jsonObject.getDouble("celsius_degree")

            return temp.toString()

        }

        override fun onPostExecute(result: String?) {

            temperature.text=result

        }
    }

}


