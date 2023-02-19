package com.mudassir.data.mapper

import com.mudassir.core.dispatcher.DispatcherProvider
import com.mudassir.core.mapper.Mapper
import com.mudassir.data.MenuItemListResponse
import com.mudassir.domain.model.Pizza
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PizzaDataToUiDataMapper  @Inject constructor(private val dispatcherProvider: DispatcherProvider) :
    Mapper<MenuItemListResponse, List<Pizza>> {

    override suspend fun invoke(input: MenuItemListResponse): List<Pizza> {
        val menuList = mutableListOf<Pizza>()
        withContext(dispatcherProvider.default()) {
            input.menuItems?.forEach {
                menuList.add(
                    Pizza(
                         name = it.name.orEmpty(),
                        image =0,
                     imageName =  it.image.orEmpty(),
                 price = 122,
//                 size: PizzaSizeAndPrice = PizzaSizeAndPrice (PizzaSize.M, 5),
//                 toppings = it.description,
                 resultPrice = 100
//                        id = it.itemId.orEmpty(),
//                        name = it.name.orEmpty(),
//                        image =it.description,
//                        price = it.price,
                    )
                )
            }
        }
        return menuList
    }

}