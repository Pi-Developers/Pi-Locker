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

package group.pals.android.lib.ui.lockpattern.prefs;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import group.pals.android.lib.ui.lockpattern.R;

public class Prefs {

    /**
     * This unique ID is used for storing preferences.
     *
     * @since v2.6 beta
     */
    public static final String UID = "a6eedbe5-1cf9-4684-8134-ad4ec9f6a131";

    /**
     * Generates global preference filename of this library.
     *
     * @param context the context.
     * @return the global preference filename.
     */
    public static final String genPreferenceFilename(Context context) {
        return String.format("%s_%s", context.getString(R.string.alp_lib_name),
                UID);
    }

    /**
     * Generates global database filename.
     *
     * @param context the context.
     * @param name    the database filename.
     * @return the global database filename.
     */
    public static final String genDatabaseFilename(Context context, String name) {
        return String.format("%s_%s_%s",
                context.getString(R.string.alp_lib_name), UID, name);
    }

    /**
     * Gets new {@link SharedPreferences}
     *
     * @param context the context.
     * @return {@link SharedPreferences}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences p(Context context) {
        // always use application context
        return context.getApplicationContext().getSharedPreferences(
                genPreferenceFilename(context), Context.MODE_MULTI_PROCESS);
    }

    /**
     * Setup {@code pm} to use global unique filename and global access mode.
     * You must use this method if you let the user change preferences via UI
     * (such as {@link PreferenceActivity}, {@link PreferenceFragment}...).
     *
     * @param context the context.
     * @param pm      {@link PreferenceManager}.
     * @since v2.6 beta
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setupPreferenceManager(Context context,
                                              PreferenceManager pm) {
        pm.setSharedPreferencesMode(Context.MODE_MULTI_PROCESS);
        pm.setSharedPreferencesName(genPreferenceFilename(context));
    }// setupPreferenceManager()
}
