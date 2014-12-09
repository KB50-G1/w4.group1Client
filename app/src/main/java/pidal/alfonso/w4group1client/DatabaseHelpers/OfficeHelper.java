package pidal.alfonso.w4group1client.DatabaseHelpers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import pidal.alfonso.w4group1client.Models.Office;
import pidal.alfonso.w4group1client.Models.OfficeType;

/**
 * Created by Sjoerd Thijsse on 12/5/2014.
 */
public class OfficeHelper extends DatabaseHelper {

    // Table name for office.
    public static final String TABLE_OFFICE_NAME = "offices";

    // Column names for offices table.
    public static final String KEY_OFFICE_ID = "office_id";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_OFFICE_TYPE = "office_type";

    private CompanyHelper companyHelper;

    public OfficeHelper(Context context) {
        super(context);
        companyHelper = new CompanyHelper(context);
    }

    // Adds an office instance.
    public void addOffice(Office office) {
        db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PHONE_NUMBER, office.getPhoneNumber());
        initialValues.put(KEY_ADDRESS, office.getAddress());
        initialValues.put(KEY_OFFICE_TYPE, office.getOfficeType().toString());

        initialValues.put(CompanyHelper.KEY_COMPANY_ID, office.getCompany().getCompanyID());

        context.getContentResolver().insert(DatabaseHelper.CONTENT_URI_OFFICE, initialValues);
    }

    // Retrieves a office instance.
    public Office getOffice(int id) {
        Uri officeUri = ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI_OFFICE, id);
        Cursor c;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                officeUri, null, null, null,
                KEY_ADDRESS + " desc");
        c = cursorLoader.loadInBackground();

        Office office = null;

        if (c.moveToFirst()) {
            office = new Office();
            office.setOfficeID(Integer.parseInt(c.getString(c.getColumnIndex(KEY_OFFICE_ID))));
            office.setPhoneNumber(Integer.parseInt(c.getString(c.getColumnIndex(KEY_PHONE_NUMBER))));
            office.setAddress(c.getString(c.getColumnIndex(KEY_ADDRESS)));
            office.setOfficeType(OfficeType.valueOf(c.getString(c.getColumnIndex(KEY_OFFICE_TYPE))));
            office.setCompany(companyHelper.getCompany(Integer.parseInt(c.getString(c.getColumnIndex(CompanyHelper.KEY_COMPANY_ID)))));
        }
        return office;
    }

    // Retrieves all office instances.
    public List<Office> getAllOffices() {
        List<Office> officeList = new ArrayList<Office>();

        Uri allOffices = DatabaseHelper.CONTENT_URI_OFFICE;
        Cursor c;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                allOffices, null, null, null,
                KEY_OFFICE_ID + " asc");
        c = cursorLoader.loadInBackground();

        if (c.moveToFirst()) {
            do {
                Office office = new Office();
                office.setOfficeID(Integer.parseInt(c.getString(c.getColumnIndex(KEY_OFFICE_ID))));
                office.setPhoneNumber(Integer.parseInt(c.getString(c.getColumnIndex(KEY_PHONE_NUMBER))));
                office.setAddress(c.getString(c.getColumnIndex(KEY_ADDRESS)));
                office.setOfficeType(OfficeType.valueOf(c.getString(c.getColumnIndex(KEY_OFFICE_TYPE))));
                office.setCompany(companyHelper.getCompany(Integer.parseInt(c.getString(c.getColumnIndex(CompanyHelper.KEY_COMPANY_ID)))));

                officeList.add(office);
            } while (c.moveToNext());
        }

        return officeList;
    }

    // Updates an office instance.
    public void updateOffice(Office office) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_PHONE_NUMBER, office.getPhoneNumber());
        updateValues.put(KEY_ADDRESS, office.getAddress());
        updateValues.put(KEY_OFFICE_TYPE, office.getOfficeType().toString());
        updateValues.put(CompanyHelper.KEY_COMPANY_ID, office.getCompany().getCompanyID());

        context.getContentResolver().update(ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI_OFFICE, office.getOfficeID()),
                updateValues,
                null,
                null);
    }

    // Deletes an office instance.
    public void deleteOffice(Office office) {
        context.getContentResolver().delete(ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI_OFFICE, office.getOfficeID()),
                null, null);

    }

}
