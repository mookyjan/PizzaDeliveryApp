package com.mudassir.domain.usecase

import com.mudassir.core.ApiResult
import com.mudassir.core.dispatcher.DispatcherProvider
import com.mudassir.domain.model.Pizza
import com.mudassir.domain.model.PizzaSize
import com.mudassir.domain.model.PizzaSizeAndPrice
import com.mudassir.domain.repo.PizzaRepo
import com.mudassir.domain.util.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MenuListUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private var coroutineDispatcherProvider = mockk<DispatcherProvider>()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @MockK
    private lateinit var repository: PizzaRepo

    private lateinit var useCase: MenuListUseCase

    private val exception = Exception("Exception occur")


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = MenuListUseCase(repository, coroutineDispatcherProvider)
    }

    @Test
    fun `MenuListUseCase returns menu list from remote data`() = coroutinesTestRule.testDispatcher.runBlockingTest {
            val response = ApiResult.Success(
                listOf(
                    Pizza(
                        name = "Chef's pizza",
                        image = 0,
                        quantity = 0,
                        price = 10,
                        size = PizzaSizeAndPrice(PizzaSize.M, 5),
                        toppings = HashSet(),
                        resultPrice = 122,
                        imageName = ""
                    )
                )
            )

            coEvery { repository.getMenuList() } returns response

            val output = useCase.execute(Unit)

            coVerify { repository.getMenuList() }

            assertEquals(output, response)
        }

    @Test
    fun `GetMenuListUseCase return empty list`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val emptyResponse = ApiResult.Success(emptyList<Pizza>())

            coEvery { repository.getMenuList() } returns emptyResponse


            val output = useCase.execute(Unit)

            assertEquals(output, ApiResult.Success(emptyList<Pizza>()))

        }


    @Test
    fun `MenuListUseCase throws error`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        coEvery { repository.getMenuList() } returns ApiResult.Exception(
            exception
        )

        val result = useCase.execute(Unit)

        testCoroutineDispatcher.resumeDispatcher()

        val error = result as ApiResult.Exception
        assertTrue(result is ApiResult.Exception)

        assertEquals(exception, error.throwable)
        assertEquals(exception.message, error.throwable.message)
    }


    @After
    fun tearDown() {
        // no-op
    }


}