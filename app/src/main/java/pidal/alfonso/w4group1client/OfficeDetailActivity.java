package pidal.alfonso.w4group1client;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import pidal.alfonso.w4group1client.DatabaseHelpers.OfficeHelper;


/**
 * An activity representing a single Office detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link OfficeListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link OfficeDetailFragment}.
 */
public class OfficeDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();

            arguments.putString(OfficeDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(OfficeDetailFragment.ARG_ITEM_ID));

            OfficeDetailFragment fragment = new OfficeDetailFragment();

            fragment.setArguments(arguments);

            getFragmentManager().beginTransaction()
                    .add(R.id.office_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, OfficeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void editOfficeDetails(View view) {

        // TODO: Display pre-populated dialog box with fields.

    }

    public void removeOfficeDetails(View view) {

        OfficeHelper officeHelper = new OfficeHelper(this);

        TextView office = (TextView) findViewById(R.id.office_detail_id);

        int officeid = Integer.parseInt(office.getText().toString());

        Log.d("DELETE", "Trying to delete office with id: " + Integer.toString(officeid));
        officeHelper.deleteOffice(officeHelper.getOffice(officeid));

        // TODO: go back to main and update list.

    }
}
