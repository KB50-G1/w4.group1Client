package pidal.alfonso.w4group1client;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pidal.alfonso.w4group1client.DatabaseHelpers.OfficeHelper;
import pidal.alfonso.w4group1client.Models.Office;

/**
 * A fragment representing a single Office detail screen.
 * This fragment is either contained in a {@link OfficeListActivity}
 * in two-pane mode (on tablets) or a {@link OfficeDetailActivity}
 * on handsets.
 */
public class OfficeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Office office;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OfficeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            // Get the office with the id we get from the intent or argument. (It's a string!!)
            office = new OfficeHelper(getActivity()).getOffice(Integer.parseInt(getArguments().getString(ARG_ITEM_ID)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_office_detail, container, false);

        // Show the office content as text.
        if (office != null) {
            ((TextView) rootView.findViewById(R.id.office_detail_id)).setText(Integer.toString(office.getOfficeID()));
            ((TextView) rootView.findViewById(R.id.office_detail_address)).setText(office.getAddress());
            ((TextView) rootView.findViewById(R.id.office_detail_phone)).setText(Integer.toString(office.getPhoneNumber()));
        }

        return rootView;
    }
}
