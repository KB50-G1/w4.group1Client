package pidal.alfonso.w4group1client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import pidal.alfonso.w4group1client.Models.Company;
import pidal.alfonso.w4group1client.Models.Office;
import pidal.alfonso.w4group1client.Models.OfficeType;


public class EditOfficeActivity extends Activity {

    private Office office;

    private EditText phoneNumber;
    private EditText address;
    private Spinner officeType;

    int officeIDintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_office);

        phoneNumber = (EditText) findViewById(R.id.phone_number_edit_text);
        address = (EditText) findViewById(R.id.address_edit_text);
        officeType = (Spinner) findViewById(R.id.office_type_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.office_types,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        officeType.setAdapter(adapter);

        officeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                office.setOfficeType(OfficeType.valueOf(parent.getItemAtPosition(position).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        officeIDintent = getIntent().getIntExtra(OfficeListActivity.IDintent, 0);
        if (officeIDintent != 0) {
            office = OfficeListActivity.officeHelper.getOffice(officeIDintent);


            phoneNumber.setText(Integer.toString(office.getPhoneNumber()));
            address.setText(office.getAddress());
            //officeType.setText(office.getOfficeType().toString());
        }

    }

    public void saveOffice(View view) {
        if (officeIDintent != 0) {
            office.setPhoneNumber(Integer.parseInt(phoneNumber.getText().toString()));
            office.setAddress(address.getText().toString());
            //office.setOfficeType(OfficeType.valueOf(officeType.getText().toString()));

            OfficeListActivity.officeHelper.updateOffice(office);
        } else {
            office = new Office();

            office.setPhoneNumber(Integer.parseInt(phoneNumber.getText().toString()));
            office.setAddress(address.getText().toString());
            //office.setOfficeType(OfficeType.valueOf(officeType.getText().toString()));

            office.setCompany(new Company(1, "Alber", "https://www.google.com"));
            OfficeListActivity.officeHelper.addOffice(office);
        }
        this.finish();
    }


}
