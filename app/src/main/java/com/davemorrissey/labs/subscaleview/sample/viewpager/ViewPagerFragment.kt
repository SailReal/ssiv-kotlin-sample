package com.davemorrissey.labs.subscaleview.sample.viewpager

import android.os.Bundle
import android.app.Fragment
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.davemorrissey.labs.subscaleview.sample.R.layout

import kotlinx.android.synthetic.main.view_pager_page.imageView

class ViewPagerFragment : Fragment() {

    private var asset: Uri? = null

    fun setAsset(asset: Uri) {
        this.asset = asset
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(layout.view_pager_page, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (asset == null && savedInstanceState?.containsKey(BUNDLE_ASSET) == true) {
            asset = Uri.parse(savedInstanceState.getString(BUNDLE_ASSET))
        }
        asset?.let {
			imageView.orientation = SubsamplingScaleImageView.ORIENTATION_USE_EXIF
			imageView.setImage(ImageSource.uri(it))
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val rootView = view
        if (rootView != null) {
            outState?.putString(BUNDLE_ASSET, asset.toString())
        }
    }

    companion object {
        private const val BUNDLE_ASSET = "asset"
    }

}
