package com.stvjuliengmail.smartmeds;

import android.graphics.Bitmap;

import com.stvjuliengmail.smartmeds.api.ImageDownloadTask;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Steven on 2/26/2018.
 */

public class ImageDownloadTaskTest {
    @Test(expected = RuntimeException.class)
    public void badUrlMakesTaskExecutionThrow() throws Exception {
        String badUrl = "http://www.blah.com/badurl.jpg";
        ImageDownloadTask task = new ImageDownloadTask();
        task.execute(badUrl);
    }

    @Test(expected = RuntimeException.class)
    public void badUrlMakesNullBitmapResult() throws Exception {
        String badUrl = "http://www.blah.com/badurl.jpg";
        ImageDownloadTask task = new ImageDownloadTask();
        Bitmap nullBitmap = task.execute(badUrl).get();
        // this test passes whether or not the bitmap is null !!!
        assertNotNull(nullBitmap);
    }
}
