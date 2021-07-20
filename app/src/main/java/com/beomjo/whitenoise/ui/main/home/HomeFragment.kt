/*
 * Designed and developed by 2021 beomjo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beomjo.whitenoise.ui.main.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelStoreOwner
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseFragment
import com.beomjo.whitenoise.databinding.FragmentHomeBinding
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.ui.adapters.HomeAdapter
import com.beomjo.whitenoise.ui.main.setting.SettingFragment
import com.beomjo.whitenoise.ui.main.track.TrackListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home,
) {
    private val homeViewModel: HomeViewModel by getViewModel()

    override val viewModelProvideOwner: ViewModelStoreOwner
        get() = activity as ViewModelStoreOwner

    override fun onStart() {
        super.onStart()
        setStatusBarColor(Color.WHITE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding { bindingViewModel() }.root
    }

    private fun FragmentHomeBinding.bindingViewModel() {
        homeVM = homeViewModel.apply { init() }
        fragment = this@HomeFragment
        adapter = HomeAdapter(object : HomeAdapter.HomeCategoryItemViewHolder.OnClickListener {
            override fun onItemClick(view: View, item: Category) {
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    addSharedElement(view, item.id.toString())
                    add(
                        R.id.fragment_container_layout,
                        TrackListFragment.newInstance(item)
                    )
                    hide(this@HomeFragment)
                    addToBackStack(null)
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    fun moveToSettingFragment() {
        parentFragmentManager.commit {
            add(
                R.id.fragment_container_layout,
                SettingFragment.newInstance()
            )
            hide(this@HomeFragment)
            addToBackStack(null)
        }
    }
}
