package com.github.hisaichi5518.konohana.typeadapter;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.hisaichi5518.konohana.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class BooleanTypeAdapterTest {

    private BooleanTypeAdapter adapter;
    private SharedPreferences prefs;

    @Before
    public void setup() {
        adapter = new BooleanTypeAdapter();
        prefs = RuntimeEnvironment.application.getSharedPreferences("tests", Context.MODE_PRIVATE);
    }

    @Test
    public void get_WithDefault() throws Exception {
        assertFalse(adapter.get(prefs, "key", false));

        adapter.set(prefs, "key", true);

        assertTrue(adapter.get(prefs, "key", false));
    }

    @Test
    public void get_WithoutDefault() throws Exception {
        assertFalse(adapter.get(prefs, "key"));

        adapter.set(prefs, "key", true);

        assertTrue(adapter.get(prefs, "key"));
    }
}