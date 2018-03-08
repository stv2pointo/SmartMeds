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
        private SourceConceptItem sourceConceptItem;

        public SourceConceptItem getSourceConceptItem ()
        {
            return sourceConceptItem;
        }

        public void setSourceConceptItem (SourceConceptItem sourceConceptItem)
        {
            this.sourceConceptItem = sourceConceptItem;
        }
    }

    //////////
    public class SourceConceptItem
    {
        private String id;

        private String name;

        private String url;

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getUrl ()
        {
            return url;
        }

        public void setUrl (String url)
        {
            this.url = url;
        }
    }

    /////////
}