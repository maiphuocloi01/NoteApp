package com.example.noteapp.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapp.R
import com.example.noteapp.data.Note
import com.example.noteapp.data.NoteViewModel
import com.example.noteapp.databinding.FragmentAddBinding

class AddFragment:  Fragment() {
    private val noteViewModel: NoteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        binding.spinner.onItemSelectedListener = sharedViewModel.listener
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add){
            insertNoteToDB()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun insertNoteToDB(){
        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.spinner.selectedItem.toString()
        val mDescription = binding.desciptionEt.text.toString()
        val validation = sharedViewModel.verityDataFromUser(mTitle, mDescription)
        if (validation){
            val newData = Note(
                0,
                mTitle,
                sharedViewModel.parsePriority(mPriority),
                mDescription
            )
            noteViewModel.insertNote(newData)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else{
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}