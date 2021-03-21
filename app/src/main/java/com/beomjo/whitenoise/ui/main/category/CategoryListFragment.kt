package com.beomjo.whitenoise.ui.main.category

import android.os.Bundle
import android.transition.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelStoreOwner
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseFragment
import com.beomjo.whitenoise.databinding.FragmentCategoryListBinding
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.model.HomeCategory
import com.beomjo.whitenoise.ui.adapters.CategoryListAdapter
import com.beomjo.whitenoise.ui.category.CategoryListViewModel
import com.beomjo.whitenoise.ui.player.PlayerManager
import com.beomjo.whitenoise.utilities.ext.getApplicationComponent
import com.beomjo.whitenoise.utilities.ext.getContentTransformWithFragment
import javax.inject.Inject

class CategoryListFragment : BaseFragment<FragmentCategoryListBinding>(
    R.layout.fragment_category_list,
    CategoryListViewModel::class
) {

    @Inject
    lateinit var playerManager: PlayerManager

    override val viewModelProvideOwner: ViewModelStoreOwner get() = this

    private val categoryListViewModel: CategoryListViewModel by getViewModel()

    private val homeCategoryMeta: HomeCategory by lazy { arguments?.getParcelable(KEY_HOME_CATEGORY)!! }

    override fun inject() {
        getApplicationComponent().categoryComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            manager = playerManager
            fragment = this@CategoryListFragment
            adapter = CategoryListAdapter(object :
                CategoryListAdapter.CategoryItemViewHolder.OnClickListener {
                override fun onItemClick(view: View, item: Category) {

                }
            })
            homeCategory = homeCategoryMeta
            viewmodel = categoryListViewModel.apply { loadCategoryList(homeCategoryMeta.path) }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.transitionName = homeCategoryMeta.id.toString()
        sharedElementEnterTransition = TransitionSet().apply {
            addTransition(ChangeImageTransform())
            addTransition(ChangeBounds())
            addTransition(ChangeTransform())
            addTransition(getContentTransformWithFragment())
        }

        sharedElementReturnTransition = TransitionSet().apply {
            addTransition(ChangeImageTransform())
            addTransition(ChangeBounds())
            addTransition(ChangeTransform())
            addTransition(getContentTransformWithFragment())
        }
    }

    fun onBackPressed() {
        parentFragmentManager.popBackStack()
        parentFragment?.startPostponedEnterTransition()
    }

    companion object {
        private const val KEY_HOME_CATEGORY = "HOME_CATEGORY"

        fun newInstance(homeCategory: HomeCategory): CategoryListFragment {
            return CategoryListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_HOME_CATEGORY, homeCategory)
                }
            }
        }
    }
}