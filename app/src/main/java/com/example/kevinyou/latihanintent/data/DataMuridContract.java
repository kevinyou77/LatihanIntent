package com.example.kevinyou.latihanintent.data;

import android.provider.BaseColumns;

/**
 * Created by Kevin You on 11/12/2017.
 */

public class DataMuridContract {

    public static class DataMuridEntry implements BaseColumns {
        public static final String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "DataSiswa";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_PHONE = "Phone";
        public static final String COLUMN_GENDER = "Gender";
        public static final String COLUMN_SHOE_SIZE = "ShoeSize";
        public static final String COLUMN_HOBBIES = "Hobbies";
    }
}
