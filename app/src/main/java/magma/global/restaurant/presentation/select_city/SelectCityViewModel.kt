package magma.global.restaurant.presentation.select_city

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import magma.global.restaurant.data.repository.DataRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SelectCityViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

}