/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.example.tvapp.adapter;

import java.util.ArrayList;

import com.example.tvapp.R;
import com.example.tvapp.R.drawable;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

public class ViewGroupExampleAdapter extends FancyCoverFlowAdapter {

	// =============================================================================
	// Private members
	// =============================================================================

	private int[] images = { drawable.ceshi, drawable.zixun, drawable.video, drawable.fm };

	// =============================================================================
	// Supertype overrides
	// =============================================================================

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Integer getItem(int i) {
		return images[i];
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
		CustomViewGroup customViewGroup = null;
		if (reuseableView != null) {
			customViewGroup = (CustomViewGroup) reuseableView;
		} else {
			customViewGroup = new CustomViewGroup(viewGroup.getContext());
			customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(700, 900));
		}

		customViewGroup.getImageView().setImageResource(this.getItem(i));

		return customViewGroup;
	}

	public static class CustomViewGroup extends LinearLayout {

		// =============================================================================
		// Child views
		// =============================================================================

		private ImageView imageView;

		private Button button;

		// =============================================================================
		// Constructor
		// =============================================================================

		private CustomViewGroup(Context context) {
			super(context);

			this.setOrientation(VERTICAL);
			this.setWeightSum(5);

			this.imageView = new ImageView(context);
			this.button = new Button(context);

			LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

			this.imageView.setLayoutParams(layoutParams);
			this.button.setLayoutParams(layoutParams);

			this.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			this.imageView.setAdjustViewBounds(true);

			this.button.setText("Goto GitHub");
			this.button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://davidschreiber.github.com/FancyCoverFlow"));
					view.getContext().startActivity(i);
				}
			});

			this.addView(this.imageView);
			// this.addView(this.button);
		}

		// =============================================================================
		// Getters
		// =============================================================================

		public ImageView getImageView() {
			return imageView;
		}
	}

}
