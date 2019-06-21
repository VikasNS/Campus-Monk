package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Search.db";
    private static final int DATABASE_VERSION = 1;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Suggestion_Table (Keyword_1 TEXT NOT NULL,Keyword_1_Array_Pattern TEXT,Keyword_2 TEXT NOT NULL,Keyword_2_Array_Pattern TEXT,Answer TEXT NOT NULL)");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
