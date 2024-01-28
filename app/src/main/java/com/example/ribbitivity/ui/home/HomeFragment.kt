package com.example.ribbitivity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.ribbitivity.MainActivity
import com.example.ribbitivity.R
import com.example.ribbitivity.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var counter = 0 // Counter to switch between happy and sad frog
//        var totalClicks = 0 // Total number of times the frog has been clicked

        val happyFrog = view.findViewById<ImageButton>(R.id.happyfrog)
        Glide.with(this).load(R.drawable.forg).into(happyFrog) // For the gif animation
        val sadFrog = view.findViewById<ImageView>(R.id.sadfrog)
        val numClicksDisplay = view.findViewById<TextView>(R.id.frogClicker)
        val numTimes = view.findViewById<TextView>(R.id.numTimes)

        val main = activity as MainActivity
        if (main.getClicks() > 0) {
            numClicksDisplay.setText("${main.getClicks()}")
        }

        happyFrog.setOnClickListener(){
            numTimes.visibility = View.VISIBLE
            counter +=1
            main.incrementClick()

            if (counter == 10){
                sadFrog.visibility = View.VISIBLE
                happyFrog.visibility = View.INVISIBLE
                Glide.with(this).load(R.drawable.sedforg).into(sadFrog) // For the gif animation
            }

            numClicksDisplay.setText("${main.getClicks()}")

        }

        sadFrog.setOnClickListener(){
            counter +=1
            main.incrementClick()

            // Revert to happy frog
            if (counter == 15){
                happyFrog.visibility = View.VISIBLE
                sadFrog.visibility = View.INVISIBLE
                counter = 0

            }

            numClicksDisplay.setText("${main.getClicks()}")

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}