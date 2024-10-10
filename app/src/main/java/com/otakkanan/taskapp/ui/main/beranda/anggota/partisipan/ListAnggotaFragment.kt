package com.otakkanan.taskapp.ui.main.beranda.anggota.partisipan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.databinding.FragmentListAnggotaBinding


class ListAnggotaFragment : Fragment() {

    private var _binding: FragmentListAnggotaBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListAnggotaBinding.inflate(layoutInflater,container,false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val task = arguments?.getParcelable<Task>(TAG)

        setupRecylerview(task!!)
    }

    private fun setupRecylerview(task: Task) {
        with(binding){
            val layoutManager = LinearLayoutManager(requireContext())
            rvListAnggota.layoutManager = layoutManager


            val adapter = ListAnggotaAdapter()
            rvListAnggota.adapter = adapter
            adapter.submitList(task.team)
        }
    }

    companion object{
        val TAG = "ListAnggotaFragment"

        fun newInstance(task: Task?): ListAnggotaFragment {
            val fragment = ListAnggotaFragment()
            val args = Bundle()
            args.putParcelable(TAG, task) // Kirim Task melalui Bundle
            fragment.arguments = args
            return fragment
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}