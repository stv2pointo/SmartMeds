package com.stvjuliengmail.smartmeds.model;

/**
 * Created by Steven on 2/3/2018.
 */

public class RxImagesResult {
    public ReplyStatus replyStatus;
    public NlmRxImages[] nlmRxImages;

    public RxImagesResult(ReplyStatus replyStatus, NlmRxImages[] nlmRxImages) {
        this.replyStatus = replyStatus;
        this.nlmRxImages = nlmRxImages;
    }

    public ReplyStatus getReplyStatus() {
        return replyStatus;
    }

    public NlmRxImages[] getNlmRxImages() {
        return nlmRxImages;
    }

    public class ReplyStatus{
        protected boolean success;
        //        protected String date;
        protected int imageCount;
        protected int totalImageCount;

        public ReplyStatus(boolean success, int imageCount, int totalImageCount) { //, String date
            this.success = success;
//            this.date = date;
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

    public class RelabelersNdc9 {
        protected String ndc9[];
        public RelabelersNdc9(String[] ndc9) {
            this.ndc9 = ndc9;
        }
        public String[] getNdc9() {
            return ndc9;
        }
    }

    public class NlmRxImages{
        public int id;
        public String ndc11;
        public int part;
        public RelabelersNdc9[] relabelersNdc9;
        public int rxcui;
        String splSetId;
        String splRootId;
        int splVersion;
        String acqDate;
        public String name;
        String labeler;
        public String imageUrl;
        int imageSize;
        String attribution;

        public NlmRxImages(int id, String ndc11, int part, RelabelersNdc9[] relabelersNdc9, int rxcui, String splSetId, String splRootId, int splVersion, String acqDate, String name, String labeler, String imageUrl, int imageSize, String attribution) {
            this.id = id;
            this.ndc11 = ndc11;
            this.part = part;
            this.relabelersNdc9 = relabelersNdc9;
            this.rxcui = rxcui;
            this.splSetId = splSetId;
            this.splRootId = splRootId;
            this.splVersion = splVersion;
            this.acqDate = acqDate;
            this.name = name;
            this.labeler = labeler;
            this.imageUrl = imageUrl;
            this.imageSize = imageSize;
            this.attribution = attribution;
        }

        public int getId() {
            return id;
        }

        public String getNdc11() {
            return ndc11;
        }

        public int getPart() {
            return part;
        }

        public RelabelersNdc9[] getRelabelersNdc9() {
            return relabelersNdc9;
        }

        public void setRelabelersNdc9(RelabelersNdc9[] relabelersNdc9) {
            this.relabelersNdc9 = relabelersNdc9;
        }

        public int getRxcui() {
            return rxcui;
        }

        public String getSplSetId() {
            return splSetId;
        }

        public String getSplRootId() {
            return splRootId;
        }

        public int getSplVersion() {
            return splVersion;
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

        public int getImageSize() {
            return imageSize;
        }

        public String getAttribution() {
            return attribution;
        }
    }
}
