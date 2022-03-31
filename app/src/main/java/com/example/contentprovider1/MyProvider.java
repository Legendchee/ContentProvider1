package com.example.contentprovider1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyProvider extends ContentProvider {
    private Context mContext;
    DBHelper mDbHelper = null;
    SQLiteDatabase db = null;
    public static final String AUTOHORITY = "com.example.contentprovider1";
    public static final int User_Code = 1;
    public static final int Job_Code = 2;

    //UriMatcher类使用：在ContentProvider 中注册URI
    private static final UriMatcher mMatcher;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //初始化
        mMatcher.addURI(AUTOHORITY, "user", User_Code);
        mMatcher.addURI(AUTOHORITY, "job", Job_Code);
        // 若URI资源路径 = content://com.example.contentprovider1/user ，则返回注册码User_Code
        // 若URI资源路径 = content://com.example.contentprovider1/job ，则返回注册码Job_Code

    }

    @Override
    public boolean onCreate() {

        Log.d("TAG", "onCreate: puppet5555555555");
        mContext = getContext();
        //在ContentProvider创建时对数据库进行初始化
        //运行在主线程，故不能做耗时操作，此处仅供展示
        mDbHelper = new DBHelper(getContext());
        db = mDbHelper.getWritableDatabase();

        //初始化两个表的数据（先清空两个表，再各加入一个记录）
        db.execSQL("delete from user");
        db.execSQL("insert into user values(1,'Carson');");
        db.execSQL("insert into user values(2,'Kobe');");

        db.execSQL("delete from job");
        db.execSQL("insert into job values(1,'Android');");
        db.execSQL("insert into job values(2,'ios');");

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //根据URI匹配 URI_CODE,从而匹配ContentProvider中相应的表名
        //该方法在最下面
        String table = getTableName(uri);

        Log.d("TAG", "query: puppet6666666");
//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

        //查询数据
        return db.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //根据URI匹配URI_CODE,从而匹配ContentProvider中对应的表名
        //该方法在最下面
        String table = getTableName(uri);

        Log.d("TAG", "insert: puppet777777");

        if(values!=null){
            Log.e("TAG", "insert: puppet"+values.toString());
        }else {
            Log.e("TAG", "insert: puppet:value===null");
        }

        //向该表添加数据
        db.insert(table, null, values);

        //当该URI的ContentProvider数据发生变化时，通过外界（即访问该ContentProvider数据的访问者）
        mContext.getContentResolver().notifyChange(uri, null);

//        //通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     * 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
     */
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mMatcher.match(uri)) {
            case User_Code:
                tableName = DBHelper.USER_TABLE_NAME;
                break;
            case Job_Code:
                tableName = DBHelper.JOB_TABLE_NAME;
                break;
        }
        return tableName;
    }

}
