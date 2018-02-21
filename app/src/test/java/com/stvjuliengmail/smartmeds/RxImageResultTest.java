package com.stvjuliengmail.smartmeds;

import com.stvjuliengmail.smartmeds.model.NlmRxImage;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Steven on 2/16/2018.
 */

public class RxImageResultTest {
    @Test
    public void createImagesWorks() throws Exception {
        NlmRxImage[] images = new NlmRxImage[2];
        images[0] = new NlmRxImage(1,111,"firstname","firsturl");
        images[1] = new NlmRxImage(2,222,"secondname","secondurl");

        RxImagesResult result = new RxImagesResult(images);

        assertEquals(2, result.getNlmRxImages().length);
    }
}
