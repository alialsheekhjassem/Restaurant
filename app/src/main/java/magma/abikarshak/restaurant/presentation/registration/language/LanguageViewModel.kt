package magma.abikarshak.restaurant.presentation.registration.language

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import magma.abikarshak.restaurant.data.repository.DataRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LanguageViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {


    fun isShowOnBoarding(): Boolean {
        return dataRepository.isShownOnBoarding()
    }
}