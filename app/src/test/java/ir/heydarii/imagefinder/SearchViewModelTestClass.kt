package ir.heydarii.imagefinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import ir.heydarii.imagefinder.features.search.SearchImageViewModel
import ir.heydarii.imagefinder.pojos.Assets
import ir.heydarii.imagefinder.pojos.Data
import ir.heydarii.imagefinder.pojos.ImageSearchResponseModel
import ir.heydarii.imagefinder.pojos.Preview
import ir.heydarii.imagefinder.repository.Repository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTestClass {

    @Mock
    lateinit var repository: Repository

    @InjectMocks
    lateinit var viewModel: SearchImageViewModel


    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val query = "Dog"


    private val imageResponse = fakeResponseProvider()

    private fun fakeResponseProvider(): ImageSearchResponseModel {
        val data = Data(Assets(Preview("url")), "description")
        return ImageSearchResponseModel(arrayListOf(data), 1, 20, query, 100)
    }

    @Before
    fun init() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
    }


    @Test
    fun resetValue_test() {
        viewModel.resetValues(query)
        assertEquals(1, viewModel.page)
        assertEquals(query, viewModel.lastSearchTerm)
        assertEquals(emptyList<String>(), viewModel.imageUrlList)
    }

    @Test
    fun checkToResetOrFetchNextPage_testReset() {
        viewModel.checkToResetOrFetchNextPage(query)
        assertEquals(1, viewModel.page)
        assertEquals(query, viewModel.lastSearchTerm)
        assertEquals(emptyList<String>(), viewModel.imageUrlList)
    }

    @Test
    fun checkToResetOrFetchNextPage_testNextPage() {
        viewModel.checkToResetOrFetchNextPage(query)
        viewModel.checkToResetOrFetchNextPage(query)
        assertEquals(2, viewModel.page)
        assertEquals(query, viewModel.lastSearchTerm)
    }

    @Test
    fun searchImage_testResetMethod() {
        giveSuccessfulResponse()
        viewModel.searchImage(query)
        assertEquals(1, viewModel.page)
        assertEquals(query, viewModel.lastSearchTerm)
        assertTrue(viewModel.imageUrlList.isNotEmpty())
    }

    @Test
    fun searchImage_testNextPageMethod() {
        giveSuccessfulResponse()
        viewModel.searchImage(query)
        viewModel.searchImage(query)
        assertEquals(2, viewModel.page)
        assertEquals(query, viewModel.lastSearchTerm)
        assertTrue(viewModel.imageUrlList.isNotEmpty())

    }

    @Test
    fun searchImage_testLiveData() {
        giveSuccessfulResponse()
        val observer = mock<Observer<List<String>>>()
        viewModel.searchResponseData().observeForever(observer)
        viewModel.searchImage(query)
        verify(observer).onChanged(arrayListOf(imageResponse.data.first().assets.preview.url))
    }

    private fun giveSuccessfulResponse() {
        whenever(repository.searchImage(query, 1)).thenReturn(Single.just(imageResponse))
        whenever(repository.searchImage(query, 2)).thenReturn(Single.just(imageResponse))
    }
}