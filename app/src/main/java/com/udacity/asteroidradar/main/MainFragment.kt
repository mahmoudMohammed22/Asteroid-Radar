package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.adapter.InfoPlantaryAdapter
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.roomDatabase.AsteroidEntitry

class MainFragment : Fragment() {

    val viewModel: MainViewModel by lazy {
        val application = requireNotNull(this.activity)

        ViewModelProvider(this,MainViewModelFactory(application.application))
            .get(MainViewModel::class.java)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)




        binding.lifecycleOwner = this

        // access in viewModel
        binding.viewModel = viewModel
        // access in recycler view to add data
        binding.asteroidRecycler.adapter = InfoPlantaryAdapter(InfoPlantaryAdapter.OnClickLisner {
            viewModel.displayItemTOScreen(it)

        })

        //to Navigate to another SCREEN
        viewModel.navigateToDetialScreen.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            if (null != it){
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.diplayItemCompler()
            }
        })


        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when(item.itemId) {
                R.id.show_all_menu -> viewModel.updataTWO()
                R.id.show_rent_menu -> viewModel.update()
                else -> viewModel.updataTWO()
            }


        return true
    }
}
