package com.example.ribbitivity.ui.timer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ribbitivity.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val timerViewModel =
            ViewModelProvider(this).get(TimerViewModel::class.java)

        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val start: Button = binding.startTimer
        start.setOnClickListener {

            var hour = binding.hour.text.toString().toIntOrNull()
            var min = binding.minute.text.toString().toIntOrNull()
            var sec = binding.second.text.toString().toIntOrNull()
            if (hour === null) { hour = 0 }
            if (min === null) { min = 0 }
            if (sec === null) { sec = 0 }

            if ( (hour+min+sec) > 0 && hour >= 0 && min in 0..60 && sec in 0..60) {
                binding.errorMsg.text = ""
                val time = (((hour * 60) + min) * 60 + sec) * 1000
                countDown(time.toLong()+1000)
                binding.hour.text = null
                binding.minute.text = null
                binding.second.text = null
            } else {
                binding.errorMsg.text = "Please enter a valid amount of time"
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun countDown(time: Long) {
        fun checkZero(unit: Long): String {
            return if (unit < 10) String.format("0%d", unit) else unit.toString()
        }

        val txtView: TextView = binding.countdown
        val timer = object: CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hour = (millisUntilFinished / 3600000) % 24
                val min = (millisUntilFinished / 60000) % 60
                val sec = (millisUntilFinished / 1000) % 60
                txtView.text = String.format("%s:%s:%s", checkZero(hour), checkZero(min), checkZero(sec))
            }
            override fun onFinish() {
                txtView.text = "00:00:00"
            }
        }
        timer.start()

        binding.reset.setOnClickListener {
            timer.cancel()
            txtView.text = "00:00:00"
        }
    }
}