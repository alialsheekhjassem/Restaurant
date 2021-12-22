package magma.abikarshak.restaurant.presentation.details.cart

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import magma.abikarshak.restaurant.data.repository.DataRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CartViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

}