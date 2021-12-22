package magma.abikarshak.restaurant.presentation.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import magma.abikarshak.restaurant.data.repository.DataRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OnBoardingViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

    fun setIsShowOnBoarding(isShown: Boolean) {
        dataRepository.setIsShownOnBoarding(isShown)
    }

}