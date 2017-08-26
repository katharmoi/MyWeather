package com.kadirkertis.myweather.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.kadirkertis.myweather.data.MyWeatherDbContract.WeatherInfoTable.COLUMN_CONDITION;
import static com.kadirkertis.myweather.data.MyWeatherDbContract.WeatherInfoTable.COLUMN_HUMIDITY;
import static com.kadirkertis.myweather.data.MyWeatherDbContract.WeatherInfoTable.COLUMN_PLACE_NAME;
import static com.kadirkertis.myweather.data.MyWeatherDbContract.WeatherInfoTable.COLUMN_TMP;
import static com.kadirkertis.myweather.data.MyWeatherDbContract.WeatherInfoTable.CONTENT_ITEM_TYPE;
import static com.kadirkertis.myweather.data.MyWeatherDbContract.WeatherInfoTable.CONTENT_URI;

/**
 * Created by Kadir Kertis on 18.7.2017.
 */
@RunWith(AndroidJUnit4.class)
public class MyWeatherContentProviderTest extends ProviderTestCase2<MyWeatherContentProvider> {

    private static final String TAG = MyWeatherContentProviderTest.class.getSimpleName();
    private MockContentResolver mockContentResolver;
    private SQLiteDatabase mDb;


    //Invalid Uri
    private static final Uri INVALID_URI = Uri.withAppendedPath(CONTENT_URI, "noops");


    public MyWeatherContentProviderTest() {
        super(MyWeatherContentProvider.class, MyWeatherDbContract.CONTENT_AUTHORITY);
    }


    @Before
    @Override
    public void setUp() throws Exception {
        setContext(InstrumentationRegistry.getTargetContext());
        super.setUp();
        mockContentResolver = getMockContentResolver();

    }

    //As this runs in an isolated context no clean up necessary
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void shouldPass() {
        assertNotNull(mockContentResolver);
    }

    @Test
    public void testUriAndGetType() {
        //Test the mime type for the weather_info table
        String mimeType = mockContentResolver.getType(MyWeatherDbContract.WeatherInfoTable.CONTENT_URI);
        Log.d(TAG, mimeType);
        assertEquals(MyWeatherDbContract.WeatherInfoTable.CONTENT_DIR_TYPE, mimeType);

        mimeType = mockContentResolver.getType(ContentUris.withAppendedId(CONTENT_URI, 5));
        assertEquals(CONTENT_ITEM_TYPE, mimeType);

    }

    //Helper Method to insert test data
    private void insertData(int numOfRows) {
        for (int i = 0; i < numOfRows; i++) {
            ContentValues testData = new ContentValues();
            testData.put(COLUMN_PLACE_NAME, "Izmir");
            testData.put(COLUMN_TMP, 35);
            testData.put(COLUMN_CONDITION, "Sunny");
            testData.put(COLUMN_HUMIDITY, 12);
            mockContentResolver.insert(CONTENT_URI, testData);
        }

    }

    //TODO Check UnsupportedOperationException
    @Test
    public void testInvalidUri() {
        mockContentResolver.getType(INVALID_URI);
    }


    @Test
    public void testQuery() {
        final String[] projection = {
                COLUMN_PLACE_NAME,
                COLUMN_CONDITION,
                COLUMN_HUMIDITY

        };

        Cursor c = null;
        try {
            //Test Empty Db No items
            c = mockContentResolver.query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

            assertEquals(0, c.getCount());

            //Test After Insert
            //Insert Three Rows of the Same Data
            int numToInsert = 5;
            insertData(numToInsert);

            //Get All Table
            c = mockContentResolver.query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

            assertEquals(numToInsert, c.getCount());

            //Test returned query has the same column
            //count with the projection
            c = mockContentResolver.query(CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);


            assertEquals(projection.length, c.getColumnCount());


            //Test query with id
            c = mockContentResolver.query(ContentUris.withAppendedId(CONTENT_URI, 1),
                    null,
                    null,
                    null,
                    null);

            c.moveToFirst();
            //Assert the same city
            assertEquals("Izmir", c.getString(c.getColumnIndex(COLUMN_PLACE_NAME)));

            //Assert only one raw with id 1 returned
            assertEquals(1, c.getCount());

            //Assert the id returned is the same as the id put to the Uri(1)
            assertEquals(1, c.getInt(0));

            /*
            /Test Projection
             */
            c = mockContentResolver.query(CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);
            int index = 0;
            if (c.moveToFirst()) {
                do {
                    assertEquals(projection[0], (c.getColumnName(0)));
                    assertEquals(projection[1], c.getColumnName(1));
                    assertEquals(projection[2], c.getColumnName(2));
                    index++;
                } while (c.moveToNext());
            }


        } finally {
            if (c != null) {
                c.close();
            }
        }


    }


    @Test
    public void testInsert() throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLACE_NAME, "Izmir");
        contentValues.put(COLUMN_TMP, 35);
        contentValues.put(COLUMN_CONDITION, "Sunny");
        contentValues.put(COLUMN_HUMIDITY, 12);

        Uri uri = mockContentResolver.insert(MyWeatherDbContract.WeatherInfoTable.CONTENT_URI, contentValues);
        assertNotNull(uri);
    }

    @Test
    public void testUpdate() {
        insertData(3);
        String testPlaceName = "Hatay";
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PLACE_NAME, testPlaceName);

        //Update All Rows
        int numUpdated = getMockContentResolver().update(CONTENT_URI,
                cv,
                null,
                null
        );

        //Assert That all row updated
        assertEquals(numUpdated, 3);

        Cursor c = null;
        try {
            c = mockContentResolver.query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

            //Assert all place name updated to test place name

            if (c.moveToFirst())
                do {
                    assertEquals(testPlaceName, c.getString(c.getColumnIndex(COLUMN_PLACE_NAME)));
                    assertEquals("Sunny", c.getString(c.getColumnIndex(COLUMN_CONDITION)));
                } while (c.moveToNext());


            ContentValues newCv = new ContentValues();
            newCv.put(COLUMN_PLACE_NAME, "Izmir");
            //Test single row update
            numUpdated = getMockContentResolver().update(ContentUris.withAppendedId(CONTENT_URI, 1),
                    newCv,
                    null,
                    null);
            assertEquals(numUpdated, 1);

            c = getMockContentResolver().query(ContentUris.withAppendedId(CONTENT_URI, 1),
                    null,
                    null,
                    null,
                    null);
            assertEquals(1, c.getCount());

            c.moveToFirst();
            assertEquals("Izmir", c.getString(c.getColumnIndex(COLUMN_PLACE_NAME)));


        } finally {
            if (c != null)
                c.close();
        }


    }

    @Test
    public void testDelete() {


        Cursor c = null;

        try {
            c = mockContentResolver.query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
            //Assert db Empty
            assertEquals(0,c.getCount());

            //Try to delete from an empty db
            int numDeleted = mockContentResolver.delete(CONTENT_URI,
                    null,null);

            assertEquals(0,numDeleted);

            insertData(2);

            numDeleted = mockContentResolver.delete(ContentUris.withAppendedId(CONTENT_URI,1),
                    null,null);

            //Assert deleted one row with id 1
            assertEquals(1,numDeleted);

            //should remain only one element at the db
            c = mockContentResolver.query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

            assertEquals(1,c.getCount());



        } finally {
            if (c != null)
                c.close();
        }

    }


}