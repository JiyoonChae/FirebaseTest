package com.jy.firebaseremoteconfig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val viewPager : ViewPager2 by lazy {
        findViewById(R.id.viewPager)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initData()
    }


       private fun initData(){
        //firebase remote config로 접근하는 것.
        var remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(
            remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0
            }
        )
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful){
                val isNameRevealed = remoteConfig.getBoolean("is_name_revealed")
                val quotes = parseQuotesJson(remoteConfig.getString("quotes"))

                displayQuotesPager(quotes, isNameRevealed)
            }
        }
    }

    private fun parseQuotesJson(json:String):List<Quote>{
        val jsonArray = JSONArray(json)
        var jsonList = emptyList<JSONObject>()
        for (index in 0 until jsonArray.length()){
            val jsonObject= jsonArray.getJSONObject(index)
            jsonObject?.let {
                jsonList = jsonList + it
            }
        }

        return jsonList.map{
            Quote(quote = it.getString("quote"), name = it.getString("name"))
        }
    }

    private fun displayQuotesPager(quotes:List<Quote>, isNameRevealed :Boolean) {
        val adapter = QuotePagerAdapter(quotes=quotes, isName = isNameRevealed)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(adapter.itemCount/2, false)
    }
}