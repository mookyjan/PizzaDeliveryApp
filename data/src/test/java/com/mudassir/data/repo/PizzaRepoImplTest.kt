package com.mudassir.data.repo

import android.content.Context
import com.mudassir.core.ApiResult
import com.mudassir.data.MenuItem
import com.mudassir.data.MenuItemListResponse
import com.mudassir.data.datasource.RemoteDataSource
import com.mudassir.data.mapper.PizzaDataToUiDataMapper
import com.mudassir.domain.model.Pizza
import com.mudassir.domain.model.PizzaSize
import com.mudassir.domain.model.PizzaSizeAndPrice
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PizzaRepoImplTest {

    private var mapper = mockk<PizzaDataToUiDataMapper>(relaxed = true)
    private val remoteDataSource = mockk<RemoteDataSource>()
    private lateinit var repositoryImpl: PizzaRepoImpl

    @Before
    fun setup() {
        repositoryImpl = PizzaRepoImpl(remoteDataSource, mapper)
    }

    @Test
    fun `get menuList success`() = runBlocking {
        val response = ApiResult.Success(
            MenuItemListResponse(
                menuItems = listOf<MenuItem>(
                    MenuItem(
                        itemId = 1,
                        name = "Chef's pizza",
                        image = "pizza1",
                        description = "",
                        price = 10.0,
                    )
                )
            )
        )
        val output = listOf(
            Pizza(
                name = "Chef's pizza",
                image = 0,
                quantity = 0,
                price = 10,
                size = PizzaSizeAndPrice(PizzaSize.M, 5),
                toppings = HashSet(),
                resultPrice = 122,
                imageName = "pizza1"
            )
        )

        coEvery { response.data?.let { mapper.invoke(it) } } returns output
        coEvery { remoteDataSource.getMenuList() } returns response

        //when
        val result = repositoryImpl.getMenuList()
        println(result)
        val success = result as ApiResult.Success
        assertTrue(success is ApiResult.Success)
        assertEquals(output, success.data)
        assertEquals(output.size, success.data?.size)
        assertEquals(output.get(0).name, success.data?.get(0)?.name)
    }
}