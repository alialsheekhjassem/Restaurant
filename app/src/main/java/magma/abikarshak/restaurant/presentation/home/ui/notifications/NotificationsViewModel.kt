package magma.abikarshak.restaurant.presentation.home.ui.notifications

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import magma.abikarshak.restaurant.data.repository.DataRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NotificationsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    override val coroutineContext: CoroutineContext,
) : ViewModel(), CoroutineScope {

}