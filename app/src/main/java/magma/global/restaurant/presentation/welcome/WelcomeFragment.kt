package magma.global.restaurant.presentation.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import dagger.android.support.AndroidSupportInjection
import magma.global.restaurant.R
import magma.global.restaurant.databinding.FragmentWelcomeBinding
import magma.global.restaurant.presentation.home.HomeActivity
import magma.global.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class WelcomeFragment : Fragment() {
    private lateinit var title: String
    private lateinit var description: String
    private var imageResource = 0
    private var position = 0
    lateinit var binding: FragmentWelcomeBinding
    private var viewPager: ViewPager2? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: WelcomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(WelcomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            position = requireArguments().getInt(ARG_PARAM1)
            title = requireArguments().getString(ARG_PARAM2)!!
            description = requireArguments().getString(ARG_PARAM3)!!
            imageResource = requireArguments().getInt(ARG_PARAM4)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        binding.btnStart.setOnClickListener {
            viewPager?.currentItem = 1
        }
        binding.txtSkip.setOnClickListener {
            goToHomeActivity()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewPager = requireActivity().findViewById(R.id.viewPager)

        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d("TAG", "onPageSelected: $position")
                hideKeyboard()
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(arg0: Int) {}
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun goToHomeActivity() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    fun hideKeyboard() {
        val view = binding.root
        val imm = requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        private const val ARG_PARAM4 = "param4"
        fun newInstance(
            position: Int,
            title: String?,
            description: String?,
            imageResource: Int
        ): WelcomeFragment {
            val fragment = WelcomeFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }
    }
}