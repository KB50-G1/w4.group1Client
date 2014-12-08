package pidal.alfonso.w4group1client.DatabaseHelpers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import pidal.alfonso.w4group1client.Models.Gallery;

/**
 * Created by Sjoerd Thijsse on 12/6/2014.
 */
public class GalleryHelper extends DatabaseHelper {

    // Table name for gallery.
    public static final String TABLE_GALLERY_NAME = "galleries";

    // Column names for galleries table.
    public static final String KEY_GALLERY_ID = "gallery_id";
    public static final String KEY_URL = "url";

    private CompanyHelper companyHelper;

    public GalleryHelper(Context context) {
        super(context);
        companyHelper = new CompanyHelper(context);
    }

    // Adds a gallery instance.
    public void addGallery(Gallery gallery) {
        db = this.getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_URL, gallery.getURL());
        initialValues.put(CompanyHelper.KEY_COMPANY_ID, gallery.getCompany().getCompanyID());

        context.getContentResolver().insert(DatabaseHelper.CONTENT_URI_GALLERY, initialValues);
    }

    // Retrieves a gallery instance.
    public Gallery getGallery(int id) {
        Uri galleryUri = ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI_GALLERY, id);
        Cursor c;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                galleryUri, null, null, null,
                KEY_URL + " desc");
        c = cursorLoader.loadInBackground();

        Gallery gallery = null;

        if (c.moveToFirst()) {
            gallery = new Gallery();
            gallery.setGalleryID(Integer.parseInt(c.getString(c.getColumnIndex(KEY_GALLERY_ID))));
            gallery.setURL(c.getString(c.getColumnIndex(KEY_URL)));
            gallery.setCompany(companyHelper.getCompany(Integer.parseInt(c.getString(c.getColumnIndex(CompanyHelper.KEY_COMPANY_ID)))));
        }
        return gallery;
    }

    // Retrieves all gallery instances.
    public List<Gallery> getAllGalleries() {
        List<Gallery> galleryList = new ArrayList<Gallery>();

        Uri allCompanies = DatabaseHelper.CONTENT_URI_GALLERY;
        Cursor c;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                allCompanies, null, null, null,
                KEY_URL + " desc");
        c = cursorLoader.loadInBackground();

        if (c.moveToFirst()) {
            do {
                Gallery gallery = new Gallery();
                gallery.setGalleryID(Integer.parseInt(c.getString(c.getColumnIndex(KEY_GALLERY_ID))));
                gallery.setURL(c.getString(c.getColumnIndex(KEY_GALLERY_ID)));
                gallery.setCompany(companyHelper.getCompany(Integer.parseInt(c.getString(c.getColumnIndex(CompanyHelper.KEY_COMPANY_ID)))));

                galleryList.add(gallery);
            } while (c.moveToNext());
        }

        return galleryList;
    }

    // Updates a gallery instance.
    public void updateGallery(Gallery gallery) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_GALLERY_ID, gallery.getGalleryID());
        updateValues.put(KEY_URL, gallery.getURL());
        updateValues.put(CompanyHelper.KEY_COMPANY_ID, gallery.getCompany().getCompanyID());

        context.getContentResolver().update(ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI_GALLERY, gallery.getGalleryID()),
                updateValues,
                null,
                null);
    }

    // Deletes a gallery instance.
    public void deleteGallery(Gallery gallery) {
        context.getContentResolver().delete(ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI_GALLERY, gallery.getGalleryID()),
                null, null);
    }

}
