package magma.abikarshak.restaurant.presentation.home.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.databinding.FragmentRestaurantsBinding
import magma.abikarshak.restaurant.model.Restaurant
import magma.abikarshak.restaurant.presentation.details.DetailsActivity
import magma.abikarshak.restaurant.utils.ViewModelFactory
import magma.abikarshak.restaurant.utils.listeners.RecyclerItemListener
import javax.inject.Inject

class RestaurantsFragment : Fragment(), RecyclerItemListener<Restaurant> {

    private var _binding: FragmentRestaurantsBinding? = null
    private var position = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var mRestaurantsAdapter: RestaurantsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            position = requireArguments().getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setupRecyclerView()
        setupData()

        return binding.root
    }

    private fun setupRecyclerView() {
        mRestaurantsAdapter.setListener(this)
        mRestaurantsAdapter.submitList(arrayListOf())
        binding.recyclerRestaurants.adapter = mRestaurantsAdapter
    }

    private fun setupData() {
        val filterArray = resources.getStringArray(R.array.home_filter)
        val restaurantList: ArrayList<Restaurant> = arrayListOf()
        for (filter in filterArray) {
            restaurantList.add(Restaurant(filter, filter, null, false, 0L))
        }
        mRestaurantsAdapter.submitList(restaurantList)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        fun newInstance(position: Int): RestaurantsFragment {
            val fragment = RestaurantsFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(item: Restaurant, index: Int) {
        val intent = Intent(requireActivity(), DetailsActivity::class.java)
        startActivity(intent)
    }
}