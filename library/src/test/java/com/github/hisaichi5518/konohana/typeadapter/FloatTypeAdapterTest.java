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

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class FloatTypeAdapterTest {

    private FloatTypeAdapter adapter;
    private SharedPreferences prefs;

    @Before
    public void setup() {
        adapter = new FloatTypeAdapter();
        prefs = RuntimeEnvironment.application.getSharedPreferences("tests", Context.MODE_PRIVATE);
    }

    @Test
    public void get_WithDefault() throws Exception {
        assertThat(adapter.get(prefs, "key", 0F)).isEqualTo(0F);

        adapter.set(prefs, "key", 1F);

        assertThat(adapter.get(prefs, "key", 0F)).isEqualTo(1F);
    }

    @Test
    public void get_WithoutDefault() throws Exception {
        assertThat(adapter.get(prefs, "key")).isEqualTo(0F);

        adapter.set(prefs, "key", 1F);

        assertThat(adapter.get(prefs, "key")).isEqualTo(1F);
    }

}