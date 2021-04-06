package com.beomjo.whitenoise.ui.main.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelStoreOwner
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseFragment
import com.beomjo.whitenoise.databinding.FragmentHomeBinding
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.ui.adapters.HomeAdapter
import com.beomjo.whitenoise.ui.main.track.TrackListFragment
import com.beomjo.whitenoise.utilities.ext.getApplicationComponent
import com.facebook.shimmer.ShimmerFrameLayout

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home,
    HomeViewModel::class,
) {

    private val homeViewModel: HomeViewModel by getViewModel()

    override val viewModelProvideOwner: ViewModelStoreOwner
        get() = activity as ViewModelStoreOwner

    override fun inject() {
        getApplicationComponent().homeComponent().create().inject(this)
    }

    override fun onStart() {
        super.onStart()
        setStatusBarColor(Color.WHITE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding { bindingViewModel() }.root
    }

    private fun FragmentHomeBinding.bindingViewModel() {
        homeVM = homeViewModel.apply { init() }
        adapter = HomeAdapter(object : HomeAdapter.HomeCategoryItemViewHolder.OnClickListener {
            override fun onItemClick(view: View, item: Category) {
                parentFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addSharedElement(view, item.id.toString())
                    .replace(
                        R.id.fragment_container_layout,
                        TrackListFragment.newInstance(item)
                    )
                    .addToBackStack(null)
                    .commit()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

}
