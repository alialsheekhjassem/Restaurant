package magma.abikarshak.restaurant.presentation.home.ui.profile

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
import magma.abikarshak.restaurant.databinding.FragmentProfileBinding
import magma.abikarshak.restaurant.presentation.edit_profile.EditProfileActivity
import magma.abikarshak.restaurant.utils.ViewModelFactory
import javax.inject.Inject

class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setUpListeners()

        return binding.root
    }

    private fun setUpListeners() {
        binding.txtEdit.setOnClickListener(this)
        binding.txtSettings.setOnClickListener(this)
        binding.txtAboutApp.setOnClickListener(this)
        binding.txtInvite.setOnClickListener(this)
        binding.txtLogout.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txt_edit -> {
                val intent = Intent(requireActivity(), EditProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.txt_settings -> {

            }
            R.id.txt_about_app -> {

            }
            R.id.txt_invite -> {

            }
            R.id.txt_logout -> {

            }
        }
    }
}