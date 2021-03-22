package com.beomjo.whitenoise.ui.main.sound

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
import com.beomjo.whitenoise.databinding.FragmentSoundListBinding
import com.beomjo.whitenoise.model.Sound
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.ui.adapters.SoundListAdapter
import com.beomjo.whitenoise.ui.player.PlayerActivity
import com.beomjo.whitenoise.ui.player.PlayerManager
import com.beomjo.whitenoise.utilities.ext.applyMaterialTransform
import com.beomjo.whitenoise.utilities.ext.getApplicationComponent
import javax.inject.Inject

class SoundListFragment : BaseFragment<FragmentSoundListBinding>(
    R.layout.fragment_sound_list,
    SoundListViewModel::class
) {

    @Inject
    lateinit var playerManager: PlayerManager

    override val viewModelProvideOwner: ViewModelStoreOwner get() = this

    private val categoryListViewModel: SoundListViewModel by getViewModel()

    private val category: Category by lazy { arguments?.getParcelable(KEY_HOME_CATEGORY)!! }

    override fun inject() {
        getApplicationComponent().soundListComponent().create().inject(this)
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
            fragment = this@SoundListFragment
            adapter = SoundListAdapter(object :
                SoundListAdapter.SoundItemViewHolder.OnClickListener {
                override fun onItemClick(view: View, item: Sound) {
                    moveToPlayerActivity(item)
                }
            })
            homeCategory = category
            viewmodel = categoryListViewModel.apply { loadCategoryList(category.path) }
        }.root
    }

    private fun moveToPlayerActivity(item: Sound) {

        if (playerManager.hasData.value == true) {
            val activity = requireActivity()
            PlayerActivity.startActivity(
                context = activity,
                startView = activity.findViewById(R.id.player_container_layout),
                sound = item,
            )
        } else {
            PlayerActivity.startActivity(context = activity, sound = item)
        }
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

        fun newInstance(category: Category): SoundListFragment {
            return SoundListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_HOME_CATEGORY, category)
                }
            }
        }
    }
}