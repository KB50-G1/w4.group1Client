package pidal.alfonso.w4group1client;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import pidal.alfonso.w4group1client.DatabaseHelpers.OfficeHelper;
import pidal.alfonso.w4group1client.Models.Office;


public class OfficeListActivity extends ListActivity {

    protected static OfficeHelper officeHelper;

    List<Office> officeList;

    protected final static String IDintent = "office_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_office_list);

        officeHelper = new OfficeHelper(this);

        officeList = officeHelper.getAllOffices();

        ArrayAdapter adapter = new ArrayAdapter<Office>(
                this,
                android.R.layout.simple_list_item_1,
                officeList
        );

        adapter.notifyDataSetChanged();

        setListAdapter(adapter);

    }


    @Override
    protected void onResume() {
        super.onResume();

        officeList = officeHelper.getAllOffices();

        ArrayAdapter adapter = new ArrayAdapter<Office>(
                this,
                android.R.layout.simple_list_item_1,
                officeList
        );

        adapter.notifyDataSetChanged();

        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Office office = officeList.get(position);

        Intent intent = new Intent(this, OfficeDetailActivity.class);
        intent.putExtra(IDintent, office.getOfficeID());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_office, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addOffice(MenuItem item) {
        Intent intent = new Intent(this, EditOfficeActivity.class);
        startActivity(intent);
    }
}
