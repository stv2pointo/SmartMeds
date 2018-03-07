package com.stvjuliengmail.smartmeds.model;

/**
 * Created by Steven on 3/7/2018.
 */

public class MyListInteractionResult
{
    private FullInteractionTypeGroup[] fullInteractionTypeGroup;

    private String nlmDisclaimer;

    public FullInteractionTypeGroup[] getFullInteractionTypeGroup ()
    {
        return fullInteractionTypeGroup;
    }

    public void setFullInteractionTypeGroup (FullInteractionTypeGroup[] fullInteractionTypeGroup)
    {
        this.fullInteractionTypeGroup = fullInteractionTypeGroup;
    }

    public String getNlmDisclaimer ()
    {
        return nlmDisclaimer;
    }

    public void setNlmDisclaimer (String nlmDisclaimer)
    {
        this.nlmDisclaimer = nlmDisclaimer;
    }


    ///////////
    public class FullInteractionTypeGroup
    {
        private FullInteractionType[] fullInteractionType;


        public FullInteractionType[] getFullInteractionType ()
        {
            return fullInteractionType;
        }

        public void setFullInteractionType (FullInteractionType[] fullInteractionType)
        {
            this.fullInteractionType = fullInteractionType;
        }
    }

    //////////

    public class FullInteractionType
    {
        private InteractionPair[] interactionPair;

        public InteractionPair[] getInteractionPair ()
        {
            return interactionPair;
        }

        public void setInteractionPair (InteractionPair[] interactionPair)
        {
            this.interactionPair = interactionPair;
        }
    }

    //////////
    public class InteractionPair
    {
        private InteractionConcept[] interactionConcept;

        private String description;

        public InteractionConcept[] getInteractionConcept ()
        {
            return interactionConcept;
        }

        public void setInteractionConcept (InteractionConcept[] interactionConcept)
        {
            this.interactionConcept = interactionConcept;
        }

        public String getDescription ()
        {
            return description;
        }

        public void setDescription (String description)
        {
            this.description = description;
        }
    }
    //////////
    public class InteractionConcept
    {
        private MinConceptItem minConceptItem;

        public MinConceptItem getMinConceptItem ()
        {
            return minConceptItem;
        }

        public void setMinConceptItem (MinConceptItem minConceptItem)
        {
            this.minConceptItem = minConceptItem;
        }
    }

    //////////
    public class MinConceptItem
    {
        private String rxcui;

        private String name;

        private String tty;

        public String getRxcui ()
        {
            return rxcui;
        }

        public void setRxcui (String rxcui)
        {
            this.rxcui = rxcui;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getTty ()
        {
            return tty;
        }

        public void setTty (String tty)
        {
            this.tty = tty;
        }
    }
    /////////
}