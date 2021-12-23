package magma.abikarshak.restaurant.presentation.registration.forget_password

import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.data.remote.controller.Resource
import magma.abikarshak.restaurant.data.remote.controller.ResponseWrapper
import magma.abikarshak.restaurant.data.remote.requests.LoginRequest
import magma.abikarshak.restaurant.data.remote.requests.ResetPasswordRequest
import magma.abikarshak.restaurant.data.remote.responses.LoginResponse
import magma.abikarshak.restaurant.data.repository.DataRepository
import magma.abikarshak.restaurant.utils.Const
import magma.abikarshak.restaurant.utils.Event
import magma.abikarshak.restaurant.utils.StringRuleUtil
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ForgetPasswordViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

    val actions = MutableLiveData<Event<ForgetPasswordActions>>()
    val resetPasswordValidation = MutableLiveData<Int>()
    val resetPasswordResponse = MutableLiveData<Event<Resource<ResponseWrapper<String>>>>()

    fun onConfirm() {
        actions.value = Event(ForgetPasswordActions.CONFIRM_CLICKED)
    }

    fun onVerify() {
        actions.value = Event(ForgetPasswordActions.VERIFY_CLICKED)
    }

    fun onSaveNewPassword() {
        actions.value = Event(ForgetPasswordActions.SAVE_NEW_PASSWORD_CLICKED)
    }

    fun onClose() {
        actions.value = Event(ForgetPasswordActions.CLOSE_CLICKED)
    }

    fun doServerSaveNewPassword(
        fullPhoneNumber: String,
        edtPassword: Editable,
        edtConfirmPassword: Editable,
        firebaseAuthToken: String
    ) {
        when {
            StringRuleUtil.NOT_EMPTY_RULE.validate(edtPassword) -> {
                resetPasswordValidation.value = Const.ERROR_PASSWORD_EMPTY
                return
            }
            StringRuleUtil.NOT_EMPTY_RULE.validate(edtPassword) -> {
                resetPasswordValidation.value = Const.ERROR_PASSWORD_EMPTY
                return
            }
            StringRuleUtil.PASSWORD_RULE.validate(edtPassword) -> {
                resetPasswordValidation.value = Const.ERROR_PASSWORD_FORMAT
                return
            }
            edtConfirmPassword.toString() != edtPassword.toString() -> {
                resetPasswordValidation.value = Const.ERROR_CONFIRM_PASSWORD_FORMAT
                return
            }
            else -> {
                launch {
                    Log.d("TAG", "doServerSaveNewPassword: $fullPhoneNumber")
                    Log.d("TAG", "doServerSaveNewPassword: $edtPassword")
                    Log.d("TAG", "doServerSaveNewPassword: $edtConfirmPassword")
                    Log.d("TAG", "doServerSaveNewPassword: $firebaseAuthToken")
                    resetPasswordResponse.value = Event(Resource.Loading())
                    val request = ResetPasswordRequest()
                    request.phone = fullPhoneNumber
                    request.password = edtPassword.toString()
                    request.firebaseAuthToken = firebaseAuthToken
                    val response: Resource<ResponseWrapper<String>> =
                        dataRepository.doServerResetPassword(request)
                    resetPasswordResponse.value = Event(response)
                }
            }
        }
    }
}