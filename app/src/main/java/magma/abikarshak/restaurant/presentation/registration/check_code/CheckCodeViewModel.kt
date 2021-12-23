package magma.abikarshak.restaurant.presentation.registration.check_code

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import magma.abikarshak.restaurant.data.remote.controller.Resource
import magma.abikarshak.restaurant.data.remote.controller.ResponseWrapper
import magma.abikarshak.restaurant.data.remote.requests.RegisterRequest
import magma.abikarshak.restaurant.data.repository.DataRepository
import magma.abikarshak.restaurant.utils.Event
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CheckCodeViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

    val actions = MutableLiveData<Event<CheckCodeActions>>()
    val registerResponse = MutableLiveData<Event<Resource<ResponseWrapper<String>>>>()

    fun doServerRegister(
        request: RegisterRequest,
        idToken: String?,
        userUID: String,
    ) {
        launch {
            Log.d("TAG", "doServerRegister: ${request.name}")
            Log.d("TAG", "doServerRegister: ${request.firebaseAuthToken}")
            Log.d("TAG", "doServerRegister: ${request.locale}")
            Log.d("TAG", "doServerRegister: ${request.phone}")
            Log.d("TAG", "doServerRegister: ${request.password}")
            Log.d("TAG", "doServerRegister: idToken $idToken")
            Log.d("TAG", "doServerRegister: userUID $userUID")
            registerResponse.value = Event(Resource.Loading())
            val response: Resource<ResponseWrapper<String>> =
                dataRepository.doServerRegister(request)
            registerResponse.value = Event(response)
        }
    }

    fun onEditPhoneNumber() {
        actions.value = Event(CheckCodeActions.EDIT_PHONE_CLICKED)
    }

    fun onVerify() {
        actions.value = Event(CheckCodeActions.VERIFY_CLICKED)
    }

    fun onResendCode() {
        actions.value = Event(CheckCodeActions.RESEND_CODE_CLICKED)
    }

}