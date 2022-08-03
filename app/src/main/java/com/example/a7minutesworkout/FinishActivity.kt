package com.example.a7minutesworkout

import android.content.Intent
import java.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.util.*

class FinishActivity : AppCompatActivity() {
    var binding :ActivityFinishBinding? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val btnfinish :Button =findViewById(R.id.btnfinish)
        btnfinish.setOnClickListener {
            finish()
            val intent =Intent(this@FinishActivity,MainActivity::class.java)
            startActivity(intent)
        }
        val dao = (application as WorkoutApp).db.HistoryDao()
        addDateToDAtabase(dao)
    }
    private fun addDateToDAtabase(historyDao: HistoryDao){

        val c =Calendar.getInstance()
        var dateTime = c.time
        Log.e("Date: ",""+dateTime)

        val sdf = SimpleDateFormat("dd MMM yyy HH:mm:ss",Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("formatted date ",""+date)

        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(date))
            Log.e("Date: ","added....")
        }
    }
}