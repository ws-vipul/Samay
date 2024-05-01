package com.vk.timemachine.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedService {

    private static final String USER_PREF_NAME = "user_pref";
    private static final String LAST_FRAG = USER_PREF_NAME + ".last_fragment";

    //TIMER UTILS
    private static final String TIMER_PREF_NAME = "timer_pref";
    private static final String TIMER_COUNT = TIMER_PREF_NAME + ".timer_count";
    private static final String IS_TIMER_RUNNING = TIMER_PREF_NAME + ".is_timer_running";


    //STOPWATCH UTILS
    private static final String STOPWATCH_PREF_NAME = "stopwatch_pref";
    private static final String STOPWATCH_COUNT = STOPWATCH_PREF_NAME + ".stopwatch_count";
    private static final String IS_STOPWATCH_RUNNING = STOPWATCH_PREF_NAME + ".is_stopwatch_running";

    public static void updateLastFragment(final String value, final Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_FRAG, value);
        editor.apply();
    }
    public static void updateTimerCount(final String value, final Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(TIMER_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TIMER_COUNT, value);
        editor.apply();
    }

    public static void updateIsTimerRunning(final String value, final Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(TIMER_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_TIMER_RUNNING, value);
        editor.apply();
    }

    public static void updateStopWatchCount(final String value, final Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(STOPWATCH_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STOPWATCH_COUNT, value);
        editor.apply();
    }

    public static void updateIsStopWatchRunning(final String value, final Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(STOPWATCH_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IS_STOPWATCH_RUNNING, value);
        editor.apply();
    }

    public static String getLastFragment(final Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREF_NAME,
                Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(LAST_FRAG, null);
        return value;
    }

    public static String getTimerCount(final Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(TIMER_PREF_NAME,
                Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(TIMER_COUNT, null);
        return value;
    }

    public static String getIsTimerRunning(final Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(TIMER_PREF_NAME,
                Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(IS_TIMER_RUNNING, null);
        return value;
    }

    public static String getStopwatchCount(final Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(STOPWATCH_PREF_NAME,
                Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(STOPWATCH_COUNT, null);
        return value;
    }

    public static String getIsStopwatchRunning(final Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(STOPWATCH_PREF_NAME,
                Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(IS_STOPWATCH_RUNNING, null);
        return value;
    }

}
