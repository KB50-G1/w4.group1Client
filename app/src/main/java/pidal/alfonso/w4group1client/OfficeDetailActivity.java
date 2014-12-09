package pidal.alfonso.w4group1client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pidal.alfonso.w4group1client.Models.Office;


public class OfficeDetailActivity extends Activity {

    private Office office;

    TextView officeID;
    TextView officeAddress;
    TextView officePhone;
    TextView officeType;

    int officeIDintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_detail);

        officeIDintent = getIntent().getIntExtra(OfficeListActivity.IDintent, 1);
        office = OfficeListActivity.officeHelper.getOffice(officeIDintent);

        officeID = (TextView) findViewById(R.id.office_detail_id);
        officeAddress = (TextView) findViewById(R.id.office_detail_address);
        officePhone = (TextView) findViewById(R.id.office_detail_phone);
        officeType = (TextView) findViewById(R.id.office_detail_type);
    }

    @Override
    protected void onResume() {
        super.onResume();

        office = OfficeListActivity.officeHelper.getOffice(officeIDintent);

        officeID.setText(Integer.toString(office.getOfficeID()));
        officeAddress.setText(office.getAddress());
        officePhone.setText(Integer.toString(office.getPhoneNumber()));
        officeType.setText(office.getOfficeType().toString());
    }

    public void editOfficeDetails(View view) {
        Intent intent = new Intent(this, EditOfficeActivity.class);
        intent.putExtra(OfficeListActivity.IDintent, office.getOfficeID());
        startActivityForResult(intent, 1234);
    }

    public void removeOfficeDetails(View view) {
        OfficeListActivity.officeHelper.deleteOffice(office);
        this.finish();
    }
}
