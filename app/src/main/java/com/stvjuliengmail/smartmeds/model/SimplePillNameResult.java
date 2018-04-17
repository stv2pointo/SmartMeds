package com.stvjuliengmail.smartmeds.model;

/**
 * Created by Steven on 4/11/2018.
 */


    public class SimplePillNameResult
    {
        private DisplayGroup displayGroup;

        public DisplayGroup getDisplayGroup ()
        {
            return displayGroup;
        }

        public void setDisplayGroup (DisplayGroup displayGroup)
        {
            this.displayGroup = displayGroup;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [displayGroup = "+displayGroup+"]";
        }

    public class DisplayGroup
    {
        private String rxcui;

        private String displayName;

        public String getRxcui ()
        {
            return rxcui;
        }

        public void setRxcui (String rxcui)
        {
            this.rxcui = rxcui;
        }

        public String getDisplayName ()
        {
            return displayName;
        }

        public void setDisplayName (String displayName)
        {
            this.displayName = displayName;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [rxcui = "+rxcui+", displayName = "+displayName+"]";
        }
    }
}
