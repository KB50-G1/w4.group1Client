package pidal.alfonso.w4group1client.DatabaseHelpers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import pidal.alfonso.w4group1client.Models.Language;

/**
 * Created by Sjoerd Thijsse on 12/6/2014.
 */
public class LanguageHelper extends DatabaseHelper {

    // Table name for language.
    public static final String TABLE_LANGUAGE_NAME = "languages";

    // Column names for languages table.
    public static final String KEY_LANGUAGE_ID = "language_id";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_DESCRIPTION = "description";

    private CompanyHelper companyHelper;

    public LanguageHelper(Context context) {
        super(context);
        companyHelper = new CompanyHelper(context);
    }

    // Adds a language instance.
    public void addLanguage(Language language) {
        db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_LANGUAGE, language.getLanguage());
        initialValues.put(KEY_DESCRIPTION, language.getDescription());
        initialValues.put(CompanyHelper.KEY_COMPANY_ID, language.getCompany().getCompanyID());

        context.getContentResolver().insert(DatabaseHelper.CONTENT_URI_LANGUAGE, initialValues);
    }

    // Retrieves a language instance.
    public Language getLanguage(int id) {
        Uri galleryUri = ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI_LANGUAGE, id);
        Cursor c;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                galleryUri, null, null, null,
                KEY_LANGUAGE + " desc");
        c = cursorLoader.loadInBackground();

        Language language = null;

        if (c.moveToFirst()) {
            language = new Language();
            language.setLanguageID(Integer.parseInt(c.getString(c.getColumnIndex(KEY_LANGUAGE_ID))));
            language.setLanguage(c.getString(c.getColumnIndex(KEY_LANGUAGE)));
            language.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            language.setCompany(companyHelper.getCompany(Integer.parseInt(c.getString(c.getColumnIndex(CompanyHelper.KEY_COMPANY_ID)))));
        }
        return language;
    }

    // Retrieves all language instances.
    public List<Language> getAllLanguages() {
        List<Language> languageList = new ArrayList<Language>();

        Uri allLanguages = DatabaseHelper.CONTENT_URI_LANGUAGE;
        Cursor c;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                allLanguages, null, null, null,
                KEY_LANGUAGE + " desc");
        c = cursorLoader.loadInBackground();

        if (c.moveToFirst()) {
            do {
                Language language = new Language();
                language.setLanguageID(Integer.parseInt(c.getString(c.getColumnIndex(KEY_LANGUAGE_ID))));
                language.setLanguage(c.getString(c.getColumnIndex(KEY_LANGUAGE)));
                language.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
                language.setCompany(companyHelper.getCompany(Integer.parseInt(c.getString(c.getColumnIndex(CompanyHelper.KEY_COMPANY_ID)))));

                languageList.add(language);
            } while (c.moveToNext());
        }

        return languageList;
    }

    // Updates a language instance.
    public void updateLanguage(Language language) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_LANGUAGE_ID, language.getLanguageID());
        updateValues.put(KEY_LANGUAGE, language.getLanguage());
        updateValues.put(KEY_DESCRIPTION, language.getDescription());
        updateValues.put(CompanyHelper.KEY_COMPANY_ID, language.getCompany().getCompanyID());

        context.getContentResolver().update(ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI_LANGUAGE, language.getLanguageID()),
                updateValues,
                null,
                null);
    }

    // Deletes a language instance.
    public void deleteLanguage(Language language) {
        context.getContentResolver().delete(ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI_LANGUAGE, language.getLanguageID()),
                null, null);
    }

}
