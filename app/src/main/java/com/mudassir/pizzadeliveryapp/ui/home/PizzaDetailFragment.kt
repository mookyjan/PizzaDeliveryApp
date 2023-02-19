package com.mudassir.pizzadeliveryapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mudassir.domain.model.Pizza
import com.mudassir.domain.model.PizzaSize
import com.mudassir.domain.model.PizzaSizeAndPrice
import com.mudassir.pizzadeliveryapp.R
import com.mudassir.pizzadeliveryapp.databinding.FragmentPizzaDetailBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class PizzaDetailFragment : Fragment() {


    val TAG = PizzaDetailFragment::class.java.name

    lateinit var mBinding: FragmentPizzaDetailBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by activityViewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentPizzaDetailBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
        setPizzaSizeButtons()
        buttonClick()

        mBinding.btnAddToCart.setOnClickListener {
            viewModel.getCurrentCount()
            Log.d(TAG, "onViewCreated: ${viewModel.currentPizzaInFocus.value}")
        }

        mBinding.toolbarBackBtn.setOnClickListener {
            Log.d(TAG, "onViewCreated: back pressed")
        }
    }


    private fun observeEvents() {
        viewModel.currentPizzaInFocus.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })

        viewModel.sizeSelected.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observeEvents: $it")
//            viewModel._currentPizzaInFocus.value?.calculatePrice(it)
            applySizeToCurrentPizzaAndDisplay(it)
        })

        viewModel.currentPizzaInFocus.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observeEvents: ${it.price} ${it.resultPrice} ")
            mBinding.tvPrice.text = "$ ${it.resultPrice}"
        })
    }


    private fun applySizeToCurrentPizzaAndDisplay(pizzaSizeAndPrice: PizzaSizeAndPrice) {
        viewModel._currentPizzaInFocus.value?.let { pizza ->
            if (pizza.size != pizzaSizeAndPrice) {
                with(pizza) {

                    resultPrice?.apply {
                        resultPrice = resultPrice!! - size.price + pizzaSizeAndPrice.price
                    }
                    size = pizzaSizeAndPrice
                }
                mBinding.tvPrice.text = ("$${pizza.resultPrice}")
            }
        }
    }

    private fun calculatePrice(pizzaSize: PizzaSize): Float {
        return when (pizzaSize) {
            PizzaSize.S -> 10.0f
            PizzaSize.M -> 12.0f
            PizzaSize.L -> 14.0f
            else -> throw java.lang.IllegalArgumentException("Invalid size: $pizzaSize")
        }
    }

    private fun setupUi() {
        mBinding.btnSizeS.setOnClickListener {
//            viewModel.updatePizzaSize(PizzaSize.S)
            it.isSelected = true
            mBinding.btnSizeM.isSelected = false
            mBinding.btnSizeL.isSelected = false
        }
        mBinding.btnSizeM.setOnClickListener {
//            viewModel.updatePizzaSize(PizzaSize.M)
            it.isSelected = true
            mBinding.btnSizeS.isSelected = false
            mBinding.btnSizeL.isSelected = false
        }
        mBinding.btnSizeL.setOnClickListener {
//            viewModel.updatePizzaSize(PizzaSize.L)
            it.isSelected = true
            mBinding.btnSizeM.isSelected = false
            mBinding.btnSizeS.isSelected = false
        }

    }

    private fun setPizzaSizeButtons() {
        val sizeButtons = listOf(mBinding.btnSizeS, mBinding.btnSizeM, mBinding.btnSizeL)

        for (button in sizeButtons) {
            button.setOnClickListener {
                viewModel.updatePizzaSize(getPizzaSizeForButton(button))
                selectPizzaSizeButton(button, sizeButtons)
            }
        }
    }

    private fun getPizzaSizeForButton(button: Button): PizzaSizeAndPrice {
        return when (button.id) {
            R.id.btnSize_S -> PizzaSizeAndPrice(PizzaSize.S, 10)
            R.id.btnSize_M -> PizzaSizeAndPrice(PizzaSize.M, 12)
            R.id.btnSize_L -> PizzaSizeAndPrice(PizzaSize.L, 15)
            else -> throw IllegalArgumentException("Unknown button")
        }
    }

    private fun selectPizzaSizeButton(selectedButton: Button, allButtons: List<Button>) {
        for (button in allButtons) {
            button.isSelected = button == selectedButton
        }
    }


    private fun onToppingButtonClick(button: Button, index: Int, currentPizzaInFocus: Pizza?) {
        with(viewModel.pizzaToppingsList[index]) {
            if (button.isSelected) {
                button.isSelected = false
                viewModel.currentPizzaInFocus?.value?.toppings?.remove(this)
                decreaseCurrentPizzaPrice(this.price)
//                if (index == 4) {
//                    mBinding.ivHotIndicator.animate().translationX(initialPosIvHotIndicator).setDuration(500)
//                }
            } else {
                button.isSelected = true
                viewModel.currentPizzaInFocus?.value?.toppings?.add(this)
                increaseCurrentPizzaPrice(this.price)
                if (index == 4) {
//                    ivHotIndicator.animate().translationX(0f).setDuration(500)
                }
            }
        }
    }

    private fun buttonClick() {

        // Set up click listeners for all topping buttons
        val toppingButtons = listOf(
            mBinding.btnAddMushrooms, mBinding.btnAddTomato,
            mBinding.btnAddBacon, mBinding.btnAddCheese, mBinding.btnAddChili
        )
        toppingButtons.forEachIndexed { index, button ->
            val topping = viewModel.pizzaToppingsList[index]
            button.setOnClickListener {
                with(topping) {
                    if (it.isSelected) {
                        it.isSelected = false
                        viewModel._currentPizzaInFocus.value?.toppings?.remove(this)
                        decreaseCurrentPizzaPrice(this.price)
                        if (index == 4) {
//                            ivHotIndicator.animate().translationX(initialPosIvHotIndicator).setDuration(500)
                        }
                    } else {
                        it.isSelected = true
                        viewModel._currentPizzaInFocus.value?.toppings?.add(this)
                        increaseCurrentPizzaPrice(this.price)
                        if (index == 4) {
//                            ivHotIndicator.animate().translationX(0f).setDuration(500)
                        }
                    }
                }
            }
        }
    }

    private fun increaseCurrentPizzaPrice(toppingPrice: Int) {
        viewModel.updatePrice(toppingPrice, true)
    }

    private fun decreaseCurrentPizzaPrice(toppingPrice: Int) {
        viewModel.updatePrice(toppingPrice, false)
    }


    private fun updateUI(pizza: Pizza) {
        mBinding.tvPizzaName.text = pizza.name
        val image = resources.getIdentifier(pizza.imageName, "drawable", context?.packageName)
        mBinding.ivPizza.setImageResource(image)
        mBinding.tvPrice.text = "$ ${pizza.price}"

    }
}