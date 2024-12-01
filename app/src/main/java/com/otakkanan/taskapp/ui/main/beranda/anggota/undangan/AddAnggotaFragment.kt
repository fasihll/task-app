package com.otakkanan.taskapp.ui.main.beranda.anggota.undangan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.otakkanan.taskapp.data.model.Task
import com.otakkanan.taskapp.data.model.Team
import com.otakkanan.taskapp.databinding.FragmentAddAnggotaBinding
import com.otakkanan.taskapp.ui.main.beranda.anggota.AnggotaActivity
import com.otakkanan.taskapp.ui.main.beranda.anggota.AnggotaActivity.Companion

class AddAnggotaFragment : Fragment() {

    private var _binding: FragmentAddAnggotaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddAnggotaBinding.inflate(layoutInflater,container,false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val teamList: ArrayList<Team>? = arguments?.getParcelableArrayList(TAG)

        setupRecyclerview(teamList)
    }

    private fun setupRecyclerview(teamList: List<Team>?) {

        with(binding) {
            val layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager
                    .VERTICAL, false
            )
            rvCehckboxAnggota.layoutManager = layoutManager


            val adapter = AddAnggotaAdapter()
            rvCehckboxAnggota.adapter = adapter
            adapter.submitList(teamList)
        }
    }

    companion object{
        val TAG = "AddAnggotaFragment"
        fun newInstance(teamList: ArrayList<Team>?): AddAnggotaFragment {
            val fragment = AddAnggotaFragment()
            val args = Bundle()
            args.putParcelableArrayList(TAG, teamList) // Kirim Task melalui Bundle
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}