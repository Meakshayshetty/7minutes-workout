package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiCalculatorBinding
import java.math.BigDecimal
import java.math.RoundingMode

class bmiCalculatorActivty : AppCompatActivity() {
    private var binding : ActivityBmiCalculatorBinding? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiCalculatorBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.BmiToolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.BmiToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btncalculate?.setOnClickListener {
            if(validateMetricUnits()){
                val heightValue :Float = binding?.textInputHeight?.text.toString().toFloat()/100
                val weightValue :Float = binding?.textInputWeight?.text.toString().toFloat()
                val bmi :Float =weightValue / ((heightValue*heightValue))
                    bmiResult(bmi)

            }else{
                Toast.makeText(this,"Please enter valid values",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun bmiResult(bmi :Float){

        var bmiLabel :String
        var bmiDescription :String

        if(bmi <= 18.5F){
            bmiLabel ="underweight"
            bmiDescription ="Oops! you realy need to take better care for yourself! Eat more"
        }
        else if(bmi in 18.5F..24.9F){
            bmiLabel ="Healthy"
            bmiDescription ="Congratulation! you are in good shape!"
        }
        else if(bmi in 24.9F..30.0F){
            bmiLabel ="Overweight"
            bmiDescription ="Oops! you realy need to take better care for yourself! Eat less"
        }else{
            bmiLabel ="Obese"
            bmiDescription ="OMG! You are in a very dangerous condition!"
        }
        val bmiValue =BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        binding?.llresult?.visibility =View.VISIBLE
        binding?.tvBMIvalue?.text = bmiValue
        binding?.BMIstatus?.text =bmiLabel
        binding?.tvBMIdescription?.text =bmiDescription

    }
        private fun validateMetricUnits() :Boolean{
            var isValid =true

            if(binding?.textInputWeight?.text.toString().isEmpty()) {
                isValid = false
            }else if(binding?.textInputHeight?.text.toString().isEmpty()){
                    isValid =false
                }
            return isValid
        }
}