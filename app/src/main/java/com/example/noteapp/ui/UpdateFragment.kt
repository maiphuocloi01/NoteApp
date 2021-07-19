package com.example.noteapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.R
import com.example.noteapp.data.Note
import com.example.noteapp.data.NoteViewModel
import com.example.noteapp.data.Priority
import com.example.noteapp.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment(R.layout.fragment_update) {
    private val sharedViewModel: SharedViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()
    private val args by navArgs<UpdateFragmentArgs>()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        binding.apply {
            textInputLayout.editText?.setText(args.currentItem.title)
            textInputLayout2.editText?.setText(args.currentItem.description)
            spinner.setSelection(parsePriority(args.currentItem.priority))
            spinner.onItemSelectedListener = sharedViewModel.listener
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updataNote()
            R.id.menu_delete -> deleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updataNote() {
        binding.apply {
            val title = textInputLayout.editText?.text.toString()
            val discription = textInputLayout2.editText?.text.toString()
            val priority = spinner.selectedItem.toString()
            val validation = sharedViewModel.verityDataFromUser(title, discription)
            if (validation) {
                val updateData = Note(
                    args.currentItem.id,
                    title,
                    sharedViewModel.parsePriority(priority),
                    discription
                )
                noteViewModel.updateNote(updateData)
                Toast.makeText(requireContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_ ->
            noteViewModel.deleteNote(args.currentItem)
            Toast.makeText(requireContext(), "Successfully Deleted!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete ${args.currentItem.title}?")
        builder.setMessage("Are you sure you want to remove ${args.currentItem.title}?")
        builder.create().show()
    }

    private fun parsePriority(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }
}
