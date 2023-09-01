package com.udacity.asteroidradar.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding
import com.udacity.asteroidradar.main.MainViewModel

class DetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private val args: DetailFragmentArgs by navArgs()

    private var isSave = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.asteroidModel = args.selectedAsteroid
        isSave = args.selectedAsteroid.isSaved
        binding.isSave = isSave

        binding.btnSave.setOnClickListener {
            if (isSave == 1) {
                isSave = 0
                viewModel.unSaveAsteroid(args.selectedAsteroid.id)
            } else {
                isSave = 1
                viewModel.saveAsteroid(args.selectedAsteroid.id)
            }
            binding.isSave = isSave
        }

        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }


        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
