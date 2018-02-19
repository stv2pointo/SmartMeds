package com.stvjuliengmail.smartmeds.model;

/**
 * Created by Steven on 2/3/2018.
 */

public class RxImagesResult {
    public ReplyStatus replyStatus;
    public NlmRxImage[] nlmRxImages;

    public RxImagesResult(ReplyStatus replyStatus, NlmRxImage[] nlmRxImages) {
        this.replyStatus = replyStatus;
        this.nlmRxImages = nlmRxImages;
    }

    public NlmRxImage[] getNlmRxImages() {
        return nlmRxImages;
    }

    public class ReplyStatus{
        protected boolean success;
        protected int imageCount;
        protected int totalImageCount;

        public ReplyStatus(boolean success, int imageCount, int totalImageCount) {
            this.success = success;
            this.imageCount = imageCount;
            this.totalImageCount = totalImageCount;
        }

        public boolean isSuccess() {
            return success;
        }

        public int getImageCount() {
            return imageCount;
        }

        public int getTotalImageCount() {
            return totalImageCount;
        }

    }

    public class NlmRxImage{
        public int id;
        public int rxcui;
        String acqDate;
        public String name;
        String labeler;
        public String imageUrl;


        public NlmRxImage(int id, int rxcui, String acqDate, String name, String labeler, String imageUrl) {
            this.id = id;
            this.rxcui = rxcui;
            this.acqDate = acqDate;
            this.name = name;
            this.labeler = labeler;
            this.imageUrl = imageUrl;
        }

        public int getId() {
            return id;
        }

        public int getRxcui() {
            return rxcui;
        }

        public String getAcqDate() {
            return acqDate;
        }

        public String getName() {
            return name;
        }

        public String getLabeler() {
            return labeler;
        }

        public String getImageUrl() {
            return imageUrl;
        }

    }

}