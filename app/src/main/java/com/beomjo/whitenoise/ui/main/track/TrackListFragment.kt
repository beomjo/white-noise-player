package com.beomjo.whitenoise.ui.main.track

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStoreOwner
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseFragment
import com.beomjo.whitenoise.databinding.FragmentTrackListBinding
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.model.Track
import com.beomjo.whitenoise.ui.adapters.TrackListAdapter
import com.beomjo.whitenoise.ui.player.PlayerActivity
import com.beomjo.whitenoise.ui.player.PlayerManager
import com.beomjo.whitenoise.utilities.ext.applyMaterialTransform
import com.beomjo.whitenoise.utilities.ext.getApplicationComponent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class TrackListFragment : BaseFragment<FragmentTrackListBinding>(
    R.layout.fragment_track_list,
    TrackListViewModel::class
) {

    @Inject
    lateinit var playerManager: PlayerManager

    override val viewModelProvideOwner: ViewModelStoreOwner get() = this

    private val trackListViewModel: TrackListViewModel by getViewModel()

    private val category: Category by lazy { arguments?.getParcelable(KEY_HOME_CATEGORY)!! }

    override fun inject() {
        getApplicationComponent().trackListComponent().create().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setStatusBarColor(category.getPrimaryColor())
        } else {
            setStatusBarColor(Color.WHITE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            fragment = this@TrackListFragment
            adapter = TrackListAdapter(object :
                TrackListAdapter.TrackItemViewHolder.OnClickListener {
                override fun onItemClick(view: View, item: Track) {
                    moveToPlayerActivity(item)
                }
            })
            homeCategory = category
            viewmodel = trackListViewModel.apply { loadTrackList(category.path) }
        }.root
    }

    private fun moveToPlayerActivity(item: Track) {
        val activity = requireActivity()
        PlayerActivity.startActivity(
            context = activity,
            startView = activity.findViewById(R.id.player_container_layout),
            track = item,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyMaterialTransform(view, category.id.toString())
    }

    fun onBackPressed() {
        parentFragmentManager.popBackStack()
        setStatusBarColor(Color.WHITE)
    }

    companion object {
        private const val KEY_HOME_CATEGORY = "HOME_CATEGORY"

        fun newInstance(category: Category): TrackListFragment {
            return TrackListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_HOME_CATEGORY, category)
                }
            }
        }
    }
}