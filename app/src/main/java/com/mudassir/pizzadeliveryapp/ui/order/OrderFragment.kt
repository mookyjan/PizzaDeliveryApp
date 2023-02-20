package com.mudassir.pizzadeliveryapp.ui.order

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.mudassir.domain.model.Pizza
import com.mudassir.pizzadeliveryapp.R
import com.mudassir.pizzadeliveryapp.databinding.FragmentOrderBinding
import com.mudassir.pizzadeliveryapp.ui.home.HomeViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class OrderFragment : Fragment(), OrderListAdapter.CartItemListener {

    val TAG = OrderFragment::class.java.name

    lateinit var mBinding: FragmentOrderBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by activityViewModels { viewModelFactory }

    private val orderViewModel : OrderViewModel by viewModels { viewModelFactory }

    lateinit var mAdapter: OrderListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentOrderBinding.inflate(inflater, container, false)
        val root: View = mBinding.root
        observeEvents()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         mAdapter = OrderListAdapter(viewModel)
        initRecyclerView()
    }

    private fun observeEvents() {
        viewModel.pizzaList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observeEvents: ob $it")
            mAdapter.submitList(it)
           val total = viewModel.calculateTotalPrice(it)
            Log.d(TAG, "observeEvents: $total")
        })
       
        mBinding.btnOrder.setOnClickListener { 
           val list = viewModel.pizzaList.value

           val arr = orderViewModel.orderRequestData(list,viewModel.totalPrice.toFloat())
            Log.d(TAG, "observeEvents: $arr")
        }

        viewModel.orderPrice.observe(viewLifecycleOwner, Observer {
            mBinding.tvOrder.text = String.format(getString(R.string.txt_total_price), it)
        })

    }


    private fun initRecyclerView() {
        mAdapter.setupListener(this)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mBinding.ryOrderList.layoutManager = layoutManager
        mBinding.ryOrderList.setHasFixedSize(true)
        mBinding.ryOrderList.adapter = mAdapter
        val dividerItemDecoration = MaterialDividerItemDecoration(
            mBinding.ryOrderList.context,
            layoutManager.orientation
        )
        mBinding.ryOrderList.addItemDecoration(dividerItemDecoration)
    }

    override fun onItemIncrement(cartItem: Int) {
//        viewModel.currentList.add(cartItem)
        Log.d(TAG, "onItemIncrement: $cartItem    ${viewModel.currentList}")
    }

    override fun onItemDecrement(cartItem: Pizza) {
        viewModel.currentList.remove(cartItem)
        Log.d(TAG, "onItemDecrement: $cartItem    ${viewModel.currentList}")
    }
}


