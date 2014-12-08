package pidal.alfonso.w4group1client.DatabaseHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Sjoerd Thijsse on 12/5/2014.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    protected Context context;
    protected SQLiteDatabase db;

    private static final String PROVIDER_NAME = "pidal.alfonso.w4group1provider.GroupOneProvider";

    protected final static Uri CONTENT_URI_COMPANY = Uri.parse("content://" + PROVIDER_NAME + "/companies");
    protected final static Uri CONTENT_URI_OFFICE = Uri.parse("content://" + PROVIDER_NAME + "/offices");
    protected final static Uri CONTENT_URI_GALLERY = Uri.parse("content://" + PROVIDER_NAME + "/galleries");
    protected final static Uri CONTENT_URI_LANGUAGE = Uri.parse("content://" + PROVIDER_NAME + "/languages");

    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
