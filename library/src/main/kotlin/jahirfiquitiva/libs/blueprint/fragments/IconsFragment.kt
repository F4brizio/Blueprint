/*
 * Copyright (c) 2017. Jahir Fiquitiva
 *
 * Licensed under the CreativeCommons Attribution-ShareAlike
 * 4.0 International License. You may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *    http://creativecommons.org/licenses/by-sa/4.0/legalcode
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Special thanks to the project contributors and collaborators
 * 	https://github.com/jahirfiquitiva/Blueprint#special-thanks
 */
package jahirfiquitiva.libs.blueprint.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.pluscubed.recyclerfastscroll.RecyclerFastScroller
import jahirfiquitiva.libs.blueprint.R
import jahirfiquitiva.libs.blueprint.adapters.IconsAdapter
import jahirfiquitiva.libs.blueprint.extensions.getInteger
import jahirfiquitiva.libs.blueprint.extensions.sortIconsList
import jahirfiquitiva.libs.blueprint.fragments.base.BaseViewModelFragment
import jahirfiquitiva.libs.blueprint.models.Icon
import jahirfiquitiva.libs.blueprint.models.IconsCategory
import jahirfiquitiva.libs.blueprint.models.viewmodels.IconItemViewModel
import jahirfiquitiva.libs.blueprint.ui.views.EmptyViewRecyclerView

class IconsFragment:BaseViewModelFragment() {

    private lateinit var model:IconItemViewModel
    private lateinit var rv:EmptyViewRecyclerView
    private lateinit var fastScroller:RecyclerFastScroller

    override fun initViewModel() {
        model = ViewModelProviders.of(this).get(IconItemViewModel::class.java)
    }

    override fun setObservableToViewModel() {
        model.items.observe(this, Observer<ArrayList<IconsCategory>> { data ->
            val adapter = rv.adapter
            if (adapter is IconsAdapter) {
                val icons = ArrayList<Icon>()
                data?.forEach {
                    icons.addAll(it.icons)
                }
                adapter.clearAndAddAll(icons.sortIconsList())
                rv.state = EmptyViewRecyclerView.STATE_NORMAL
            }
        })
    }

    override fun loadDataFromViewModel() {
        model.loadData(activity)
    }

    override fun getContentLayout():Int = R.layout.section_icons_preview

    override fun initUI(content:View) {
        rv = content.findViewById(R.id.icons_grid)
        fastScroller = content.findViewById(R.id.fast_scroller)
        rv.emptyView = content.findViewById(R.id.empty_view)
        rv.textView = content.findViewById(R.id.empty_text)
        rv.adapter = IconsAdapter {
            onItemClicked(it)
        }
        val columns = context.getInteger(R.integer.icons_grid_width)
        rv.layoutManager = GridLayoutManager(context, columns,
                                             GridLayoutManager.VERTICAL, false)
        rv.state = EmptyViewRecyclerView.STATE_LOADING
        fastScroller.attachRecyclerView(rv)
    }

    override fun onItemClicked(item:Any) {
        if (item is Icon) {
            TODO("Not implemented yet")
        }
    }
}