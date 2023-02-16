package com.example.gptbros.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
//import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gptbros.databinding.FragmentHomeBinding


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


        _binding = FragmentHomeBinding.inflate(inflater, container, false)


//        val textView: TextView = binding.textHome
////        val buttonText: TextView = binding.buttonRecord
////        buttonText.text = "Record"
////
////        val button2 : Button = binding.buttonRecord
////        button2.setOnClickListener{
////            println("clicked button 2")
////            Toast.makeText(view?.context, "Button Clicked", Toast.LENGTH_LONG).show()
////        }
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.textHome.text="This is an exceptionally hardcoded string"
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it

        }

        binding.buttonRecord.setOnClickListener {
            Toast.makeText(view.context, "New exercise will be generated", Toast.LENGTH_SHORT).show()
            println("Generated a new exercise")

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}