package magma.abikarshak.restaurant.presentation.home.ui.my_order

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.databinding.FragmentMyOrderBinding
import magma.abikarshak.restaurant.model.Restaurant
import magma.abikarshak.restaurant.utils.ViewModelFactory
import magma.abikarshak.restaurant.utils.listeners.RecyclerItemListener
import javax.inject.Inject

class MyOrderFragment : Fragment(), RecyclerItemListener<Restaurant> {

    private var _binding: FragmentMyOrderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var mOrdersAdapter: OrdersAdapter

    private val viewModel: MyOrderViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MyOrderViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyOrderBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUp()
        setupData()

        return binding.root
    }

    private fun setupData() {
        val filterArray = resources.getStringArray(R.array.home_filter)
        val restaurantList: ArrayList<Restaurant> = arrayListOf()
        for (filter in filterArray) {
            restaurantList.add(Restaurant(filter, filter, null, false, 0L))
        }
        mOrdersAdapter.submitList(restaurantList)
    }

    private fun setUp() {
        mOrdersAdapter.setListener(this)
        mOrdersAdapter.submitList(arrayListOf())
        binding.recyclerOrders.adapter = mOrdersAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(item: Restaurant, index: Int) {

    }
}