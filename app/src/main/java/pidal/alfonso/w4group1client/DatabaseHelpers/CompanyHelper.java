package pidal.alfonso.w4group1client.DatabaseHelpers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import pidal.alfonso.w4group1client.Models.Company;

/**
 * Created by Sjoerd Thijsse on 12/5/2014.
 */
public class CompanyHelper extends DatabaseHelper {

    // Table name for company.
    public static final String TABLE_COMPANY_NAME = "companies";

    // Column names for company table.
    public static final String KEY_COMPANY_ID = "company_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_WEBSITE = "website";


    public CompanyHelper(Context context) {
        super(context);

    }

    // Adds a company instance.
    public void addCompany(Company company) {
        db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, company.getName());
        initialValues.put(KEY_WEBSITE, company.getWebsite());

        context.getContentResolver().insert(DatabaseHelper.CONTENT_URI_COMPANY, initialValues);
    }

    // Retrieves a company instance.
    public Company getCompany(int id) {
        Uri companyUri = ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI_COMPANY, id);
        Cursor c;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                companyUri, null, null, null,
                KEY_NAME + " desc");
        c = cursorLoader.loadInBackground();

        Company company = null;

        if (c.moveToFirst()) {
            company = new Company();
            company.setCompanyID(Integer.parseInt(c.getString(c.getColumnIndex(KEY_COMPANY_ID))));
            company.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            company.setWebsite(c.getString(c.getColumnIndex(KEY_WEBSITE)));
        }
        return company;
    }

    // Retrieves all company instances.
    public List<Company> getAllCompanies() {
        List<Company> companyList = new ArrayList<Company>();

        Uri allCompanies = DatabaseHelper.CONTENT_URI_COMPANY;
        Cursor c;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                allCompanies, null, null, null,
                KEY_NAME + " desc");
        c = cursorLoader.loadInBackground();

        if (c.moveToFirst()) {
            do {
                Company company = new Company();
                company.setCompanyID(Integer.parseInt(c.getString(c.getColumnIndex(KEY_COMPANY_ID))));
                company.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                company.setWebsite(c.getString(c.getColumnIndex(KEY_WEBSITE)));

                companyList.add(company);
            } while (c.moveToNext());
        }

        return companyList;
    }

    // Updates a company instance.
    public void updateCompany(Company company) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_NAME, company.getName());
        updateValues.put(KEY_WEBSITE, company.getWebsite());

        context.getContentResolver().update(ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI_COMPANY, company.getCompanyID()),
                updateValues,
                null,
                null);
    }

    // Deletes a company instance.
    public void deleteCompany(Company company) {
        context.getContentResolver().delete(ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI_COMPANY, company.getCompanyID()),
                null, null);
    }

}

