package com.mudassir.pizzadeliveryapp.ui.home

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.mudassir.core.ApiResult
import com.mudassir.core.hide
import com.mudassir.core.show
import com.mudassir.domain.model.Pizza
import com.mudassir.pizzadeliveryapp.R
import com.mudassir.pizzadeliveryapp.databinding.FragmentHomeBinding
import com.mudassir.pizzadeliveryapp.databinding.PizzaViewsBinding
import com.mudassir.pizzadeliveryapp.ui.adapter.MenuListAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class HomeFragment : Fragment(), MenuListAdapter.Callbacks {

    private val TAG = HomeFragment::class.simpleName
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val mBinding get() = _binding!!

    lateinit var mBindingPizzaView: PizzaViewsBinding


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by activityViewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    private val menuListAdapter = MenuListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = mBinding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel.menuLiveDataEvent.postValue(Unit)
        observeEvents()
    }


    private fun initRecyclerView() {
        menuListAdapter.setupListener(this)
        val layoutManager = GridLayoutManager(context, 2)
        mBinding.rvMenuList.layoutManager = layoutManager
        mBinding.rvMenuList.setHasFixedSize(true)
        mBinding.rvMenuList.adapter = menuListAdapter
        // Set the spacing between items in the grid
        val spacing = resources.getDimensionPixelSize(R.dimen.grid_spacing)

        // Create the item decoration for vertical dividers
        val verticalDividerItemDecoration =
            MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        verticalDividerItemDecoration.dividerColor = resources.getColor(R.color.black)

        // Create the item decoration for horizontal dividers
        val horizontalDividerItemDecoration =
            MaterialDividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
        horizontalDividerItemDecoration.dividerColor = resources.getColor(R.color.black)


        // Set the margin for the items in the grid to create the divider effect
        val itemDecoration = object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(spacing, spacing, spacing, spacing)
            }
        }
        mBinding.rvMenuList.addItemDecoration(verticalDividerItemDecoration)
        mBinding.rvMenuList.addItemDecoration(horizontalDividerItemDecoration)
        mBinding.rvMenuList.addItemDecoration(itemDecoration)
    }


    private fun observeEvents() {
        viewModel.menusLiveData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "observeEvents: $it")
            when (it) {
                is ApiResult.Loading -> {
                    showProgressBar()
                }
                is ApiResult.Success -> {
                    Log.d(TAG, "Success: ${it.data}")
                    hideProgressBar()
                    //if the user retry and response is success hide error layout
                    mBinding.lyOffline.root.hide()
                    menuListAdapter.submitList(it.data)
                }
                is ApiResult.Exception -> {
                    hideProgressBar()
                    Log.d(TAG, "Exception: ${it.throwable}")
                    showErrorLayout()
                }
                is ApiResult.Error -> {
                    hideProgressBar()
                    Log.d(TAG, "Error ${it.message}")
                    showErrorLayout()
                }
            }
        })
    }


    /**
     * show progress bar
     */
    private fun showProgressBar() {
        mBinding.progressCircular.show()
    }

    private fun hideProgressBar() {
        mBinding.progressCircular.hide()
    }


    /**
     * method to show the error screen when api fail
     */
    private fun showErrorLayout() {
        mBinding.lyOffline.root.show()
        mBinding.lyOffline.tvErrorTitle.text =
            getString(R.string.txt_error_title)
        mBinding.lyOffline.tvErrorDetail.text =
            getString(R.string.txt_error_description)
        mBinding.lyOffline.btnRetry.text = getString(R.string.btn_retry)
        mBinding.lyOffline.btnRetry.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPizzaItemClick(view: View, item: Pizza) {

        viewModel.updateSelectedPizza(item)
        val action = HomeFragmentDirections.actionNavigationHomeToPizzaDetailFragment()
        findNavController().navigate(action)
    }
}