package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    var binding :ActivityHistoryBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarExcercise)
        if(supportActionBar !=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title ="HISTORY"
        }
        binding?.toolBarExcercise?.setNavigationOnClickListener {
            onBackPressed()
        }
        val dao = (application as WorkoutApp).db.HistoryDao()
        getAllcompletedDates(dao)
    }
    private fun getAllcompletedDates(historyDao: HistoryDao){

        lifecycleScope.launch {
            historyDao.fetchAllDates().collect{ allCompletedDatesList ->
                if(allCompletedDatesList.isNotEmpty()){
                    binding?.rvHistory?.visibility =View.VISIBLE
                    binding?.tvavailable?.visibility =View.INVISIBLE
                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    val dates =ArrayList<String>()
                    for(i in allCompletedDatesList){
                        dates.add(i.date)
                    }
                    val historyAdaptor =historyAdaptor(dates)
                    binding?.rvHistory?.adapter = historyAdaptor

                }else{
                    binding?.rvHistory?.visibility =View.GONE
                    binding?.tvavailable?.visibility =View.VISIBLE
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}