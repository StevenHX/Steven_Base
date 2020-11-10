package com.hx.mediaselect;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.hx.mediaselect.util.AppUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("com.hx.mediaselect.test", appContext.getPackageName());
        assertEquals("com.hx.mediaselect.test", AppUtil.getLastPathSegment("/storage/emulated/0/DCIM/Camera/IMG_20170621_174717.jpg"));
    }
}