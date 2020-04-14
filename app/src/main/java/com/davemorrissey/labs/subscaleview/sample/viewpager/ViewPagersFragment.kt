package com.davemorrissey.labs.subscaleview.sample.viewpager

import android.app.Fragment
import android.app.FragmentManager
import android.support.v13.app.FragmentPagerAdapter
import android.view.View

import com.davemorrissey.labs.subscaleview.sample.AbstractPagesFragment
import com.davemorrissey.labs.subscaleview.sample.Page

import java.util.Arrays

import com.davemorrissey.labs.subscaleview.sample.R.layout.view_pager
import com.davemorrissey.labs.subscaleview.sample.R.string.pager_p1_subtitle
import com.davemorrissey.labs.subscaleview.sample.R.string.pager_p1_text
import com.davemorrissey.labs.subscaleview.sample.R.string.pager_p2_subtitle
import com.davemorrissey.labs.subscaleview.sample.R.string.pager_p2_text
import com.davemorrissey.labs.subscaleview.sample.R.string.pager_title

import kotlinx.android.synthetic.main.view_pager.horizontal_pager as horizontalPager
import kotlinx.android.synthetic.main.view_pager.vertical_pager as verticalPager

class ViewPagersFragment : AbstractPagesFragment(pager_title, view_pager, Arrays.asList(
        Page(pager_p1_subtitle, pager_p1_text),
        Page(pager_p2_subtitle, pager_p2_text)
)) {

    override fun onResume() {
        super.onResume()
        horizontalPager.adapter = ScreenSlidePagerAdapter(childFragmentManager)
        verticalPager.adapter = ScreenSlidePagerAdapter(childFragmentManager)
    }

    override fun onPageChanged(page: Int) {
        if (page == 0) {
            horizontalPager.visibility = View.VISIBLE
            verticalPager.visibility = View.GONE
        } else {
            horizontalPager.visibility = View.GONE
            verticalPager.visibility = View.VISIBLE
        }
    }

    private inner class ScreenSlidePagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            val fragment = ViewPagerFragment()
            fragment.setAsset(IMAGES[position])
            return fragment
        }

        override fun getCount(): Int {
            return IMAGES.size
        }
    }

    companion object {

        private val IMAGES = arrayOf(
				"landscape_1.jpg",
				"landscape_2.jpg",
				"landscape_3.jpg",
				"landscape_4.jpg",
				"landscape_5.jpg",
				"landscape_6.jpg",
				"landscape_7.jpg",
				"landscape_8.jpg",
				"portrait_1.jpg",
				"portrait_2.jpg",
				"portrait_3.jpg",
				"portrait_4.jpg",
				"portrait_5.jpg",
				"portrait_6.jpg",
				"portrait_7.jpg",
				"portrait_8.jpg"
				)
    }

}
