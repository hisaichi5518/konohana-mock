package com.github.hisaichi5518.konohana.prefsadapter;

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
public class EnumPrefsAdapterTest {

    private SharedPreferences prefs;

    @Before
    public void setup() {
        prefs = RuntimeEnvironment.application.getSharedPreferences("tests", Context.MODE_PRIVATE);
    }

    @Test
    public void get_WithDefault() throws Exception {
        assertThat(EnumPrefsAdapter.get(prefs, "key", Type.none)).isEqualTo(Type.none);

        EnumPrefsAdapter.set(prefs, "key", Type.buyer);

        assertThat(EnumPrefsAdapter.get(prefs, "key", Type.none)).isEqualTo(Type.buyer);
    }

    private enum Type {
        none, buyer
    }
}