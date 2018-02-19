package com.stvjuliengmail.smartmeds.model;

public class RxImagesResult {
    private NlmRxImage[] nlmRxImages;

    public RxImagesResult(NlmRxImage[] nlmRxImages) {
        this.nlmRxImages = nlmRxImages;
    }

    public NlmRxImage[] getNlmRxImages() {
        return nlmRxImages;
    }

    public class NlmRxImage {
        private int id;
        private int rxcui;
        private String name;
        private String imageUrl;

        public NlmRxImage(int id, int rxcui, String name, String imageUrl) {
            this.id = id;
            this.rxcui = rxcui;
            this.name = name;
            this.imageUrl = imageUrl;
        }

        public int getId() {
            return id;
        }

        public int getRxcui() {
            return rxcui;
        }

        public String getName() {
            return name;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
}