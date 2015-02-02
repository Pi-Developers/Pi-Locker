/*
 *   Copyright 2012 Hai Bison
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package group.pals.android.lib.ui.lockpattern.util;

import group.pals.android.lib.ui.lockpattern.BuildConfig;
import group.pals.android.lib.ui.lockpattern.R;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;

/**
 * UI utilities.
 * 
 * @author Hai Bison
 */
public class UI {

    private static final String CLASSNAME = UI.class.getName();

    /**
     * Uses a fixed size for {@code dialog} in large screens.
     * 
     * @param dialog
     *            the dialog.
     */
    public static void adjustDialogSizeForLargeScreen(Dialog dialog) {
        adjustDialogSizeForLargeScreen(dialog.getWindow());
    }// adjustDialogSizeForLargeScreen()

    /**
     * Uses a fixed size for {@code dialogWindow} in large screens.
     * 
     * @param dialogWindow
     *            the window <i>of the dialog</i>.
     */
    public static void adjustDialogSizeForLargeScreen(final Window dialogWindow) {
        if (BuildConfig.DEBUG)
            Log.d(CLASSNAME, "adjustDialogSizeForLargeScreen()");
        if (dialogWindow.isFloating()
                && dialogWindow.getContext().getResources()
                        .getBoolean(R.bool.alp_is_large_screen)) {
            final DisplayMetrics metrics = dialogWindow.getContext()
                    .getResources().getDisplayMetrics();
            final boolean isPortrait = metrics.widthPixels < metrics.heightPixels;

            int width = metrics.widthPixels;// dialogWindow.getDecorView().getWidth();
            int height = metrics.heightPixels;// dialogWindow.getDecorView().getHeight();
            if (BuildConfig.DEBUG)
                Log.d(CLASSNAME, String.format("width = %,d | height = %,d",
                        width, height));
            width = (int) dialogWindow
                    .getContext()
                    .getResources()
                    .getFraction(
                            isPortrait ? R.dimen.aosp_dialog_fixed_width_minor
                                    : R.dimen.aosp_dialog_fixed_width_major,
                            width, width);
            height = (int) dialogWindow
                    .getContext()
                    .getResources()
                    .getFraction(
                            isPortrait ? R.dimen.aosp_dialog_fixed_height_major
                                    : R.dimen.aosp_dialog_fixed_height_minor,
                            height, height);
            if (BuildConfig.DEBUG)
                Log.d(CLASSNAME, String.format(
                        "NEW >>> width = %,d | height = %,d", width, height));
            dialogWindow.setLayout(width, height);
        }
    }// adjustDialogSizeForLargeScreen()

    /**
     * Convenient method for {@link Context#getTheme()} and
     * {@link Resources.Theme#resolveAttribute(int, TypedValue, boolean)}.
     * 
     * @param context
     *            the context.
     * @param resId
     *            The resource identifier of the desired theme attribute.
     * @return the resource ID that {@link TypedValue#resourceId} points to, or
     *         {@code 0} if not found.
     */
    public static int resolveAttribute(Context context, int resId) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(resId, typedValue, true))
            return typedValue.resourceId;
        return 0;
    }// resolveAttribute()
}
