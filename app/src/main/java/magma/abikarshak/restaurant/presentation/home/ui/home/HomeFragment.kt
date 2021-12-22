package magma.abikarshak.restaurant.presentation.home.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.databinding.FragmentHomeBinding
import magma.abikarshak.restaurant.model.Image
import magma.abikarshak.restaurant.model.Restaurant
import magma.abikarshak.restaurant.presentation.details.DetailsActivity
import magma.abikarshak.restaurant.utils.ViewModelFactory
import magma.abikarshak.restaurant.utils.listeners.RecyclerItemFoodListener
import magma.abikarshak.restaurant.utils.listeners.RecyclerItemListener
import javax.inject.Inject

class HomeFragment : Fragment(), RecyclerItemListener<Restaurant>,
    RecyclerItemFoodListener<Restaurant> {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var mAdapterSliderView: AdapterSliderView

    @Inject
    lateinit var mRestaurantAdapter: RestaurantsAdapter

    @Inject
    lateinit var mFoodsAdapter: FoodsAdapter

    @Inject
    lateinit var mNewFoodsAdapter: FoodsAdapter

    @Inject
    lateinit var mFoodTypeAdapter: FoodTypeAdapter

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setup()

        return binding.root
    }

    private fun setup() {
        val mSliderView = binding.imageSlider
        mAdapterSliderView = AdapterSliderView()
        mSliderView.setSliderAdapter(mAdapterSliderView)
        mSliderView.indicatorSelectedColor = Color.WHITE
        mSliderView.indicatorUnselectedColor = Color.GRAY
        mSliderView.scrollTimeInSec = 3
        mSliderView.isAutoCycle = true
        mSliderView.startAutoCycle()
        setSliderImages(arrayListOf())

        setupRecyclerView()
        setupData()
    }

    private fun setupRecyclerView() {
        mRestaurantAdapter.setListener(this)
        mRestaurantAdapter.submitList(arrayListOf())
        binding.recyclerRecommendedRestaurants.adapter = mRestaurantAdapter

        mFoodsAdapter.setListener(this)
        mFoodsAdapter.submitList(arrayListOf())
        binding.recyclerRecommendedFood.adapter = mFoodsAdapter

        mNewFoodsAdapter.setListener(this)
        mNewFoodsAdapter.submitList(arrayListOf())
        binding.recyclerNewItems.adapter = mNewFoodsAdapter

        mFoodTypeAdapter.setListener(this)
        mFoodTypeAdapter.submitList(arrayListOf())
        binding.recyclerFoodType.adapter = mFoodTypeAdapter
    }

    private fun setupData() {
        val filterArray = resources.getStringArray(R.array.home_filter)
        val restaurantList: ArrayList<Restaurant> = arrayListOf()
        for (filter in filterArray) {
            restaurantList.add(Restaurant(filter, filter, null, false, 0L))
        }
        mRestaurantAdapter.submitList(restaurantList)
        mFoodsAdapter.submitList(restaurantList)
        mNewFoodsAdapter.submitList(restaurantList)
        mFoodTypeAdapter.submitList(restaurantList)
    }

    private fun setSliderImages(images: ArrayList<Image>) {
        val path =
            "https://bucketeer-f52451a2-710a-48b0-a193-073e6441909f.s3.eu-west-1.amazonaws.com/public/images/categories/image619e02fe356f2.png"
        val image = Image(1, path)
        images.add(image)
        Log.d("TAG", "setSliderImages: $images")
        mAdapterSliderView.setImages(images)
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
        val intent = Intent(requireActivity(), DetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onIncClicked(item: Restaurant, index: Int) {

    }

    override fun onDecClicked(item: Restaurant, index: Int) {
    }

    override fun onAddClicked(item: Restaurant, index: Int) {
    }
}