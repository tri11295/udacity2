package com.udacity.asteroidradar.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.TypeFilter
import com.udacity.asteroidradar.api.RetrofitClient
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.loadUrl
import com.udacity.asteroidradar.room.AsteroidModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainFragment : Fragment() {

    private val dayOfWeek = getNextSevenDaysFormattedDates()

    private lateinit var binding: FragmentMainBinding

    private val asteroidAdapter: AsteroidAdapter by lazy {
        AsteroidAdapter {
            findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        }
    }

    private val viewModel: MainViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.initDataBase(requireContext())
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeLiveData()
    }

    private fun setUpViews() {
        binding.rvAsteroid.adapter = asteroidAdapter
    }

    private fun initData() {
        viewModel.getPictureOfDay()
    }

    private fun observeLiveData() {
        viewModel.allAsteroids.observe(viewLifecycleOwner) { list ->
            viewModel.listAllAsteroids = list
            viewModel.listAsteroidsByWeek = list.filter {
                dayOfWeek.contains(it.closeApproachDate)
            }
            viewModel.listAsteroidsToDay = list.filter {
                it.closeApproachDate == dayOfWeek.first()
            }
            setDataRvAsteroid(viewModel.typeFilter)
        }
        viewModel.savedAsteroids.observe(viewLifecycleOwner) {
            viewModel.listSavedAsteroids = it.orEmpty()
        }

        viewModel.showErrorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.getPictureOfDayLiveData.observe(viewLifecycleOwner) {
            if (it?.mediaType == "image") {
                binding.ivOfTheDay.loadUrl(it.url.orEmpty())
            }
            binding.ivOfTheDay.contentDescription =
                getString(R.string.nasa_picture_of_day_content_description_format, it?.title)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_all_menu -> {
                viewModel.typeFilter = TypeFilter.WEEK.type
                setDataRvAsteroid(TypeFilter.WEEK.type)
                return true
            }

            R.id.show_rent_menu -> {
                viewModel.typeFilter = TypeFilter.TO_DAY.type
                setDataRvAsteroid(TypeFilter.TO_DAY.type)
                return true
            }

            R.id.show_buy_menu -> {
                viewModel.typeFilter = TypeFilter.SAVE.type
                setDataRvAsteroid(TypeFilter.SAVE.type)
                return true
            }
        }
        return true
    }

    private fun setDataRvAsteroid(typeFilter: Int) {
        val listDisplay = when (typeFilter) {
            TypeFilter.WEEK.type -> viewModel.listAsteroidsByWeek
            TypeFilter.TO_DAY.type -> viewModel.listAsteroidsToDay
            else -> {
                viewModel.listSavedAsteroids
            }
        }
        asteroidAdapter.submitList(listDisplay)
    }

    private fun getNextSevenDaysFormattedDates(): ArrayList<String> {
        val formattedDateList = ArrayList<String>()

        val calendar = Calendar.getInstance()
        for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
            val currentTime = calendar.time
            val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            formattedDateList.add(dateFormat.format(currentTime))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return formattedDateList
    }
}
