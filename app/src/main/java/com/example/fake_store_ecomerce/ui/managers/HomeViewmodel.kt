import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.fake_store_ecomerce.data.models.ProductResponse
import com.example.fake_store_ecomerce.data.repo.ProductRepository
import kotlinx.coroutines.flow.Flow

class HomeViewmodel : ViewModel() {
    private val repository = ProductRepository()

    val productsFlow: Flow<PagingData<ProductResponse>> = repository
        .getProductsPaging()
        .cachedIn(viewModelScope)
}