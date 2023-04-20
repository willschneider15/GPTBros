package com.example.gptbros.ui.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.gptbros.databinding.FragmentAccountBinding
import com.example.gptbros.model.FolderListItem
import com.example.gptbros.model.GptBrosRepository
import com.example.gptbros.ui.folder.AccountViewModel
import com.example.gptbros.ui.folder.AccountViewModelFactory
import com.example.gptbros.ui.folder.FolderListItemAdapter
import com.example.gptbros.ui.folder.FolderViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val args: AccountFragmentArgs by navArgs()
    private val accountViewModel : AccountViewModel by viewModels {
        AccountViewModelFactory(args.sessionId)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        accountViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {



                launch {
                    accountViewModel.summary.collect { item ->
                        Log.d("AccountFragment", item.toString())
                        binding.textDashboard.text = item.content
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