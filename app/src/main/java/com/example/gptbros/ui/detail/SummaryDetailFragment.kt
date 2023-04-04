package com.example.gptbros.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gptbros.R
import com.example.gptbros.databinding.FragmentFolderBinding
import com.example.gptbros.databinding.FragmentSummaryDetailBinding
import com.example.gptbros.ui.folder.FolderListItemAdapter
import com.example.gptbros.ui.folder.FolderViewModel
import kotlinx.coroutines.launch

class SummaryDetailFragment : Fragment() {
    private var _binding: FragmentSummaryDetailBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }


    //private val summaryDetailFragmentArgs : SummaryDetailFragmentArgs by navArgs()
//    private val summaryDetailViewModel : SummaryDetailViewModel by viewModels {
//        SummaryDetailViewModelFactory(summaryDetailFragmentArgs.sessionUUID)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSummaryDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
////        folderViewModel.text.observe(viewLifecycleOwner) {
////            textView.text = it
////        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //As long as the the fragment is in STARTED or higher the the view will be updated with new summaries and their status
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
//                    summaryDetailViewModel.summaryItem.collect { item ->
//                        binding.summaryContent.text = item.content
//                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}