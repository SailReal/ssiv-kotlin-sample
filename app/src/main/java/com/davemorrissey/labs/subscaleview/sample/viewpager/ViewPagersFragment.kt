package com.davemorrissey.labs.subscaleview.sample.viewpager

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.content.Intent
import android.net.Uri
import android.support.v13.app.FragmentPagerAdapter
import android.support.v4.provider.DocumentFile
import android.view.View
import com.davemorrissey.labs.subscaleview.sample.AbstractPagesFragment
import com.davemorrissey.labs.subscaleview.sample.Page
import com.davemorrissey.labs.subscaleview.sample.R.layout.view_pager
import com.davemorrissey.labs.subscaleview.sample.R.string.*
import java.util.*
import kotlinx.android.synthetic.main.view_pager.horizontal_pager as horizontalPager
import kotlinx.android.synthetic.main.view_pager.vertical_pager as verticalPager


class ViewPagersFragment : AbstractPagesFragment(pager_title, view_pager, Arrays.asList(
        Page(pager_p1_subtitle, pager_p1_text),
        Page(pager_p2_subtitle, pager_p2_text)
)) {

	var uri: Uri? = null

    override fun onResume() {
        super.onResume()

		if(uri == null) {
			val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
			intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
			intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)

			startActivityForResult(intent, REQUEST_CODE)
		} else {
			horizontalPager.adapter = ScreenSlidePagerAdapter(childFragmentManager)
			verticalPager.adapter = ScreenSlidePagerAdapter(childFragmentManager)
		}
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

	val REQUEST_CODE = 20

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			val uri = data?.data
			uri?.let {
				context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
				this.uri = it

				horizontalPager.adapter = ScreenSlidePagerAdapter(childFragmentManager)
				verticalPager.adapter = ScreenSlidePagerAdapter(childFragmentManager)
			}
		}
	}

    private inner class ScreenSlidePagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

		var uris = DocumentFile.fromTreeUri(context, uri).listFiles()

        override fun getItem(position: Int): Fragment {
            val fragment = ViewPagerFragment()

			fragment.setAsset(uris.get(position).uri)

            return fragment
        }

        override fun getCount(): Int {
            return uris.size
        }
    }
}
