package com.example.gptbros.ui.folder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gptbros.databinding.FragmentFolderBinding
import kotlinx.coroutines.launch

class FolderFragment : Fragment() {

    private var _binding: FragmentFolderBinding? = null

    private val folderViewModel : FolderViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFolderBinding.inflate(inflater, container, false)
        binding.folderRecyclerView.layoutManager = LinearLayoutManager(context)
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
                    folderViewModel.folderListItems.collect { items ->
                        binding.folderRecyclerView.adapter = FolderListItemAdapter(requireContext(), items)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}