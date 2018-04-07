package com.stvjuliengmail.smartmeds.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

/**
 * Created by shaht_000 on 4/4/2018.
 */

public class PillAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private ArrayList<String> pillNameList;

    public PillAutoCompleteAdapter(Context context, int textViewResourceId)
    {
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        return pillNameList.size();
    }

    @Override
    public String getItem(int index) {
        return pillNameList.get(index);
    }

    @Override
    public Filter getFilter()
    {
        Filter filter = new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                FilterResults filterResults = new FilterResults();
                if (constraint != null)
                {
                    // Retrieve the autocomplete results.
                    //pillNameList = autocomplete(constraint.toString());

                    // Assign the data to the FilterResults
                    filterResults.values = pillNameList;
                    filterResults.count = pillNameList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results)
            {
                if (results != null && results.count > 0)
                {
                    notifyDataSetChanged();
                } else
                {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

//    private ArrayList<String> autocomplete(String input)
//    {
//        ArrayList<String> pillNameList = null;
//        HttpURLConnection conn = null;
//        StringBuilder jsonResults = new StringBuilder();
//
//        try {
//            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
//
//            sb.append("?sensor=true&key=" + API_KEY);
//            String pName = getPillName();
//
//            if (pName != null) {
//                pillName = pName;
//            } else {
//                pillName = "za";
//            }
//            sb.append("&components=pill:" + pillName);
//            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
//
//            URL url = new URL(sb.toString());
//
//            conn = (HttpURLConnection) url.openConnection();
//
//            InputStreamReader in = new InputStreamReader(conn.getInputStream());
//
//        }
//        catch (MalformedURLException e)
//        {
//
//            Log.e(TAG, "Error processing Places API URL", e);
//
//            return pillNameList;
//
//        } catch (IOException e)
//        {
//
//            Log.e(TAG, "Error connecting to Places API", e);
//
//            return pillNameList;
//
//        }
//            return pillNameList;
//    }
}


            //    private static final int MAX_RESULTS = 1000;
//    private Context mContext;
//    private List<Pill> resultList = new ArrayList<Pill>();
//
//    public PillAutoCompleteAdapter(Context context)
//    {
//        mContext = context;
//    }
//
//    @Override
//    public int getCount()
//    {
//        return resultList.size();
//    }
//
//    @Override
//    public Pill getItem(int index)
//    {
//        return resultList.get(index);
//    }
//
//    @Override
//    public long getItemId(int position)
//    {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent)
//    {
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) mContext
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.activity_search, parent, false);
//        }
//        ((AutoCompleteTextView) convertView.findViewById(R.id.text1)).setText(getItem(position).getPillName());
//        return convertView;
//    }
//
//    @Override
//    public Filter getFilter()
//    {
//        Filter filter = new Filter()
//        {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults filterResults = new FilterResults();
//                if (constraint != null) {
//                    List<Pill> pills = findPills(mContext, constraint.toString());
//
//                    // Assign the data to the FilterResults
//                    filterResults.values = pills;
//                    filterResults.count = pills.size();
//                }
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                if (results != null && results.count > 0) {
//                    resultList = (List<Pill>) results.values;
//                    notifyDataSetChanged();
//                } else {
//                    notifyDataSetInvalidated();
//                }
//            }};
//        return filter;
//    }
//
//    private List<Pill> findPills(Context context, String pillNames) {
//        // GoogleBooksProtocol is a wrapper for the Google Books API
//        /*
//        *I need to find the pill API to replace GoogleBooksProtocol
//         */
//        //GoogleBooksProtocol protocol = new GoogleBooksProtocol(context, MAX_RESULTS);
//        //return protocol.findPills(pillNames);
//    }





