package magma.abikarshak.restaurant.presentation.registration.register

import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.hbb20.CountryCodePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import magma.abikarshak.restaurant.data.remote.controller.Resource
import magma.abikarshak.restaurant.data.remote.controller.ResponseWrapper
import magma.abikarshak.restaurant.data.remote.requests.LoginRequest
import magma.abikarshak.restaurant.data.remote.requests.RegisterRequest
import magma.abikarshak.restaurant.data.remote.responses.LoginResponse
import magma.abikarshak.restaurant.data.repository.DataRepository
import magma.abikarshak.restaurant.utils.Const
import magma.abikarshak.restaurant.utils.Event
import magma.abikarshak.restaurant.utils.StringRuleUtil
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RegisterViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

    //val registerResponse = MutableLiveData<Event<Resource<ResponseWrapper<String>>>>()
    val registerRequest = MutableLiveData<RegisterRequest>()
    val actions = MutableLiveData<Event<RegisterActions>>()
    val registerValidation = MutableLiveData<Int>()
    val loginResponse = MutableLiveData<Event<Resource<LoginResponse>>>()

    fun doServerRegister(
        edtName: Editable,
        countryCodePicker: CountryCodePicker,
        edtPhone: Editable,
        edtPassword: Editable,
        edtConfirmPassword: Editable,
        edtEmail: Editable
    ) {

        val fullPhoneNumber: String?

        when {
            StringRuleUtil.NOT_EMPTY_RULE.validate(edtName) -> {
                registerValidation.value = Const.ERROR_NAME_EMPTY
                return
            }
            StringRuleUtil.NOT_EMPTY_RULE.validate(edtPhone) -> {
                registerValidation.value = Const.ERROR_PHONE_EMPTY
                return
            }
            StringRuleUtil.PHONE_RULE.validate(edtPhone) -> {
                registerValidation.value = Const.ERROR_PHONE_FORMAT
                return
            }
            StringRuleUtil.NOT_EMPTY_RULE.validate(edtPassword) -> {
                registerValidation.value = Const.ERROR_PASSWORD_EMPTY
                return
            }
            StringRuleUtil.PASSWORD_RULE.validate(edtPassword) -> {
                registerValidation.value = Const.ERROR_PASSWORD_FORMAT
                return
            }
            edtConfirmPassword.toString() != edtPassword.toString() -> {
                registerValidation.value = Const.ERROR_CONFIRM_PASSWORD_FORMAT
                return
            }
            (edtEmail.toString().isNotEmpty() && StringRuleUtil.EMAIL_RULE.validate(edtEmail)) -> {
                registerValidation.value = Const.ERROR_EMAIL_FORMAT
                return
            }
            else -> {
                fullPhoneNumber = countryCodePicker.fullNumberWithPlus
                val request = RegisterRequest()
                request.name = edtName.toString()
                request.phone = fullPhoneNumber
                request.password = edtPassword.toString()
                request.locale = dataRepository.getLang()
                registerRequest.value = request
            }
        }

    }

    fun doServerLogin(googleSignInAccount: GoogleSignInAccount) {
        launch {
            Log.d("TAG", "doServerLogin: ${googleSignInAccount.email}")
            Log.d("TAG", "doServerLogin: ${googleSignInAccount.idToken}")
            loginResponse.value = Event(Resource.Loading())
            val request = LoginRequest()
            /*
            * This Request not processed from API
            * */
            request.phone = googleSignInAccount.idToken
            request.password = ""
            val response: Resource<LoginResponse> =
                dataRepository.doServerLogin(request)
            loginResponse.value = Event(response)
        }
    }

    fun onSignIn() {
        actions.value = Event(RegisterActions.SIGN_IN_CLICKED)
    }

    fun onGoogleSignIn() {
        actions.value = Event(RegisterActions.GOOGLE_CLICKED)
    }

    fun onFacebookSignIn() {
        actions.value = Event(RegisterActions.FACEBOOK_CLICKED)
    }

}