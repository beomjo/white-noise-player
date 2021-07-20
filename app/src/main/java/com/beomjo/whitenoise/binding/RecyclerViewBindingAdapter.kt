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

package com.beomjo.whitenoise.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.model.Track
import com.beomjo.whitenoise.ui.adapters.HomeAdapter
import com.beomjo.whitenoise.ui.adapters.TrackListAdapter

object RecyclerViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("adapterCategoryList")
    fun bindAdapterHomeItemList(recyclerView: RecyclerView, categories: List<Category>?) {
        categories?.let { (recyclerView.adapter as HomeAdapter).addItems(it) }
    }

    @JvmStatic
    @BindingAdapter("adapterTrackList")
    fun bindAdapterCategoryItemList(recyclerView: RecyclerView, tracks: List<Track>?) {
        tracks?.let { (recyclerView.adapter as TrackListAdapter).addItems(it) }
    }
}
