package com.example.a7minutesworkout
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding :ActivityExerciseBinding? =null
    private var restTimer :CountDownTimer? =null
    private var restProgress =0
    private var restTimerDuration :Long =10
    private var workoutTimer :CountDownTimer? =null
    private var workoutProgress =0
    private var exerciseTimerDuration :Long =10
    private var exerciseList :ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var tts :TextToSpeech? =null
    private var player :MediaPlayer? =null
    private var exerciseAdapter : ExerciseStatusAdapter? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarExcercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        exerciseList =Constants.defaultExerciseList()

        tts = TextToSpeech(this,this)

        binding?.toolBarExcercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        setUpRestView()
        setupExerciseStatusRecyclerview()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
        //super.onBackPressed()
    }

    private fun customDialogForBackButton(){
       val customDialog =Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnyes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnno.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    private fun setupExerciseStatusRecyclerview(){
      binding?.rvExerciseStatus?.layoutManager =
          LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter =ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setUpRestView(){
        player?.stop()

        binding?.flProgressBar?.visibility =View.VISIBLE
        binding?.tvready?.visibility = View.VISIBLE
        binding?.flProgressBar2?.visibility =View.INVISIBLE
        binding?.tvexerciseName?.visibility =View.INVISIBLE
        binding?.exerciseIamge?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility =View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility =View.VISIBLE

        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
            binding?.tvUpcomingExerciseName?.text =
                exerciseList!![currentExercisePosition+1].getName()

        setRestProgressBar()
    }
    private fun setupExerciseView(){

        try {
            //have try without uriString
            val soundUri = Uri.parse(
                "android.resource://com.example.a7minutesworkout/"
                        +R.raw.press_start_mp3.toString())
            player =MediaPlayer.create(applicationContext,soundUri)
            player?.isLooping = false
            player?.start()
            player?.setVolume(0.0F,0.2F)
        }catch (e :Exception){
            e.printStackTrace()
        }
        binding?.flProgressBar?.visibility =View.INVISIBLE
        binding?.tvready?.visibility = View.INVISIBLE
        binding?.flProgressBar2?.visibility =View.VISIBLE
        binding?.tvexerciseName?.visibility =View.VISIBLE
        binding?.exerciseIamge?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility =View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility =View.INVISIBLE


        if(workoutTimer != null){
            workoutTimer?.cancel()
            workoutProgress = 0
        }
        speakOut(exerciseList!![currentExercisePosition].getName())

        binding?.exerciseIamge?.
        setImageResource(exerciseList!![currentExercisePosition].getimage())
        binding?.tvexerciseName?.text = exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }
    private fun setRestProgressBar(){
        binding?.progressBar?.progress =restProgress

        restTimer =object: CountDownTimer(restTimerDuration*1000,1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress =10 -restProgress
                binding?.tvtimer?.text =(10 - restProgress).toString()

            }
            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
    }
    private fun setExerciseProgressBar(){
        binding?.progressBar2?.progress =workoutProgress

        workoutTimer =object: CountDownTimer(exerciseTimerDuration*3000,1000){
            override fun onTick(p0: Long) {
                workoutProgress++
                binding?.progressBar2?.progress =30 -workoutProgress
                binding?.tvtimer2?.text =(30 - workoutProgress).toString()
            }
            override fun onFinish() {
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()


                if(currentExercisePosition < exerciseList?.size!! -1){
                    setUpRestView()
                }else{
                    finish()
                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.ENGLISH)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language specified is not supported")
            }
        }else{
            Log.e("TTS","initialization failed")
        }
    }
    private fun speakOut(text :String){
        tts!!.speak(text,TextToSpeech.QUEUE_ADD,null,null)
    }
    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (workoutTimer != null) {
            workoutTimer?.cancel()
            workoutProgress = 0
        }
        if(tts !=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player !=null){
            player!!.stop()
        }
        binding = null
    }
}