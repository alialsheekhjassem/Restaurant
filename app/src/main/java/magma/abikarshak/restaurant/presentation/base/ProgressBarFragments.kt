package magma.abikarshak.restaurant.presentation.base

import android.app.AlertDialog
import android.content.Intent
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.presentation.home.HomeActivity
import magma.abikarshak.restaurant.utils.BindingUtils.hideKeyboard
import magma.abikarshak.restaurant.utils.CommonUtils
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

/**
 * Class extends MessagesFragments, handles loading processes.
 * */
open class ProgressBarFragments : Fragment() {

    private lateinit var alertDialog: AlertDialog

    fun showLoadingDialog() {
        alertDialog = CommonUtils.showLoadingDialog(requireActivity())
    }

    fun hideLoadingDialog() {
        alertDialog.cancel()
    }

    fun goToHomeActivity() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    fun showErrorToast(error: String){
        MotionToast.darkToast(
            requireActivity(),
            getString(R.string.error),
            error,
            MotionToastStyle.ERROR,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
        )
    }

    fun showSuccessToast(success: String){
        MotionToast.darkToast(
            requireActivity(),
            success,
            getString(R.string.code_sent),
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular)
        )
    }

    open fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }
}