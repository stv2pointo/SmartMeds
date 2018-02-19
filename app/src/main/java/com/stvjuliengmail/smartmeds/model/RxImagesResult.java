package com.stvjuliengmail.smartmeds.model;

public class RxImagesResult {
    private NlmRxImage[] nlmRxImages;

    public RxImagesResult(NlmRxImage[] nlmRxImages) {
        this.nlmRxImages = nlmRxImages;
    }

    public NlmRxImage[] getNlmRxImages() {
        return nlmRxImages;
    }


}