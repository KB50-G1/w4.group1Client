package pidal.alfonso.w4group1client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import pidal.alfonso.w4group1client.DatabaseHelpers.CompanyHelper;
import pidal.alfonso.w4group1client.DatabaseHelpers.OfficeHelper;
import pidal.alfonso.w4group1client.Models.Office;
import pidal.alfonso.w4group1client.Models.OfficeType;


public class EditOfficeActivity extends Activity {

    private Office office;
    private CompanyHelper companyHelper;
    private OfficeHelper officeHelper;

    private EditText phoneNumber;
    private EditText address;
    private Spinner officeType;

    int officeIDintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_office);

        companyHelper = new CompanyHelper(this);
        officeHelper = new OfficeHelper(this);

        phoneNumber = (EditText) findViewById(R.id.phone_number_edit_text);
        address = (EditText) findViewById(R.id.address_edit_text);
        officeType = (Spinner) findViewById(R.id.office_type_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.office_types,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        officeType.setAdapter(adapter);

        office = new Office();

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
            office = officeHelper.getOffice(officeIDintent);

            phoneNumber.setText(Integer.toString(office.getPhoneNumber()));
            address.setText(office.getAddress());
        }

    }

    public void saveOffice(View view) {
        if (officeIDintent != 0) {
            if (phoneNumber.getText().toString().matches("") && address.getText().toString().matches("")) {
                // TODO: dialog box to prompt user
                this.InvalidDialogBox()
                ;
            } else {
                office.setPhoneNumber(Integer.parseInt(phoneNumber.getText().toString()));
                office.setAddress(address.getText().toString());

                officeHelper.updateOffice(office);

                this.finish();
            }

        } else {
            if (phoneNumber.getText().toString().matches("") && address.getText().toString().matches("")) {
                // TODO: dialog box to prompt user
                this.InvalidDialogBox();
            } else {

                office.setPhoneNumber(Integer.parseInt(phoneNumber.getText().toString()));
                office.setAddress(address.getText().toString());

                office.setCompany(companyHelper.getCompany(1));
                officeHelper.addOffice(office);

                this.finish();
            }
        }
    }


    private void InvalidDialogBox() {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Invalid address and/or phonenumber")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
