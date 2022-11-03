package com.example.songplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mp : MediaPlayer? = null
    private var currentSong : MutableList<Int> = mutableListOf(R.raw.boomslang)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controlSound(currentSong[0])

    }

    private fun controlSound(id: Int){

        fab_play.setOnClickListener{
            if (mp == null){
                mp = MediaPlayer.create(this,id)
                Log.d("MainActivity", "ID: ${mp!!.audioSessionId}")

                initialiseSeekBar()
            }
            mp?.start()
            Log.d("MainActivity", "Duration: ${mp!!.duration/1000} seconds")
        }

        fab_pause.setOnClickListener {
            if (mp !== null) mp?.pause()
            Log.d("MainActivity", "Paused at: ${mp!!.duration/1000} seconds")
        }

        fab_stop.setOnClickListener {
            if (mp !== null){
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun initialiseSeekBar() {
        seekBar.max = mp!!.duration

        val handler = Handler()
        handler.postDelayed(object:Runnable {
            override fun run() {
                try{
                seekBar.progress = mp!!.currentPosition
                handler.postDelayed( this, 1000)
            } catch (e: Exception) {
                seekBar.progress = 0

                }
            }
        }, 0)
    }
}