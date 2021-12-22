package magma.abikarshak.restaurant.presentation.registration.login

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
import magma.abikarshak.restaurant.data.remote.responses.LoginResponse
import magma.abikarshak.restaurant.data.repository.DataRepository
import magma.abikarshak.restaurant.utils.Const
import magma.abikarshak.restaurant.utils.Event
import magma.abikarshak.restaurant.utils.StringRuleUtil
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

    val actions = MutableLiveData<Event<LoginActions>>()
    val loginValidation = MutableLiveData<Int>()
    val loginResponse = MutableLiveData<Event<Resource<ResponseWrapper<LoginResponse>>>>()

    fun doServerLogin(
        fcmToken: String,
        countryCodePicker: CountryCodePicker,
        edtPhone: Editable,
        edtPassword: Editable
    ) {

        val fullPhoneNumber: String?

        //validate phone number
        when {
            StringRuleUtil.NOT_EMPTY_RULE.validate(edtPhone) -> {
                loginValidation.value = Const.ERROR_EMPTY
                return
            }
            StringRuleUtil.PHONE_RULE.validate(edtPhone) -> {
                loginValidation.value = Const.ERROR_PHONE
                return
            }
            StringRuleUtil.PASSWORD_RULE.validate(edtPassword) -> {
                loginValidation.value = Const.ERROR_PASSWORD
                return
            }
            else -> {
                fullPhoneNumber = countryCodePicker.fullNumberWithPlus
                launch {
                    Log.d("TAG", "doServerLogin: $fullPhoneNumber")
                    Log.d("TAG", "doServerLogin: $edtPassword")
                    Log.d("TAG", "doServerLogin: $fcmToken")
                    loginResponse.value = Event(Resource.Loading())
                    val request = LoginRequest()
                    request.phone = fullPhoneNumber
                    request.password = edtPassword.toString()
                    request.firebaseFCMToken = fcmToken
                    val response: Resource<ResponseWrapper<LoginResponse>> =
                        dataRepository.doServerLogin(request)
                    loginResponse.value = Event(response)
                }
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
            val response: Resource<ResponseWrapper<LoginResponse>> =
                dataRepository.doServerLogin(request)
            loginResponse.value = Event(response)
        }

    }

    fun doServerGoogleSignIn() {
        actions.value = Event(LoginActions.GOOGLE_CLICKED)
    }

    fun onSignUp() {
        actions.value = Event(LoginActions.SIGN_UP_CLICKED)
    }

    fun onForgetPassword() {
        actions.value = Event(LoginActions.FORGET_PASSWORD)
    }

    fun saveToken(loginResponse: LoginResponse) {
        loginResponse.token?.let { dataRepository.setApiToken(it) }
    }

}