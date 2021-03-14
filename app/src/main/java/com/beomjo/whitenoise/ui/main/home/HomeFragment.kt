package com.beomjo.whitenoise.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStoreOwner
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseFragment
import com.beomjo.whitenoise.databinding.FragmentHomeBinding
import com.beomjo.whitenoise.model.HomeCategory
import com.beomjo.whitenoise.ui.adapters.HomeAdapter
import com.beomjo.whitenoise.ui.category.CategoryListActivity
import com.beomjo.whitenoise.ui.player.PlayerManager
import com.beomjo.whitenoise.utilities.ext.getApplicationComponent
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home,
    HomeViewModel::class,
) {

    @Inject
    lateinit var playerManager: PlayerManager

    private val homeViewModel: HomeViewModel by getViewModel()

    override val viewModelProvideOwner: ViewModelStoreOwner
        get() = activity as ViewModelStoreOwner

    override fun inject() {
        getApplicationComponent().homeComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            homeVM = homeViewModel
            manager = playerManager
            adapter = HomeAdapter(object : HomeAdapter.HomeItemViewHolder.OnClickListener {
                override fun onItemClick(view: View, item: HomeCategory) {
                    CategoryListActivity.startActivity(
                        context = requireActivity(),
                        startView = view,
                        playerView = (this@HomeFragment.view?.findViewById(R.id.player_layout))!!,
                        homeCategory = item
                    )
                }
            })
        }.root
    }
}