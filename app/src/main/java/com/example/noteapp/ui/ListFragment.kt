package com.example.noteapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.R
import com.example.noteapp.data.NoteViewModel
import com.example.noteapp.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private val noteViewModel: NoteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private val adapter: ListAdapter by lazy { ListAdapter() }
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        noteViewModel.getAllNote.observe(viewLifecycleOwner, Observer { data ->
            sharedViewModel.checkIfDataEmpty(data)
            adapter.setData(data)
        })
        sharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabase(it)
        })
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        return binding.root
    }

    private fun showEmptyDatabase(emptyDB: Boolean) {
        if (emptyDB){
            binding.imageView.visibility = View.VISIBLE
            binding.textView.visibility = View.VISIBLE
        }
        else{
            binding.imageView.visibility = View.INVISIBLE
            binding.textView.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all){
            deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_ ->
            noteViewModel.deleteAll()
            Toast.makeText(requireContext(), "Successfully Deleted Everything!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}