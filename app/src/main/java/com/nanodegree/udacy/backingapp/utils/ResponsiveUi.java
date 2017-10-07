package com.nanodegree.udacy.backingapp.utils;

import android.util.DisplayMetrics;

import com.nanodegree.udacy.backingapp.R;


/**
 * Created by tarekkma on 6/22/17.
 */

public class ResponsiveUi {
  public static boolean isTablet() {
    return BackingAppApplication.get().getResources().getBoolean(R.bool.isTablet);
  }

  public static boolean isLandscape() {
    return BackingAppApplication.get().getResources().getBoolean(R.bool.isLandscape);
  }

  public static int getCoulumnNumber() {
    if (isTablet()) {

      int itemWidthDp = 150;
      double multiplier = (isTablet()) ? .5 : 1;
      DisplayMetrics displayMetrics = BackingAppApplication.get().getResources().getDisplayMetrics();
      float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
      dpWidth *= multiplier;
      int res = (int) (dpWidth / itemWidthDp);

     return (res == 0) ? 1 : res;
    } else if (isLandscape()) {
      return 2;
    } else {
      return 1;
    }
  }
}
