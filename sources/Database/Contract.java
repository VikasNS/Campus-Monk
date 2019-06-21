package Database;

import android.provider.BaseColumns;

public final class Contract {

    public static class Navigation_table_variables implements BaseColumns {
        public static final String COLOUMN_NAME_ASNWER = "Answer";
        public static final String COLOUMN_NAME_KEYWORD_1 = "Keyword_1";
        public static final String COLOUMN_NAME_KEYWORD_1_ARRAY_PATTERN = "Keyword_1_Array_Pattern";
        public static final String COLOUMN_NAME_KEYWORD_2 = "Keyword_2";
        public static final String COLOUMN_NAME_KEYWORD_2_ARRAY_PATTERN = "Keyword_2_Array_Pattern";
        public static final String TABLE_NAME = "Navigation_Table";
    }

    public static class Suggestion_table_variables implements BaseColumns {
        public static final String COLOUMN_NAME_ASNWER = "Answer";
        public static final String COLOUMN_NAME_KEYWORD_1 = "Keyword_1";
        public static final String COLOUMN_NAME_KEYWORD_1_ARRAY_PATTERN = "Keyword_1_Array_Pattern";
        public static final String COLOUMN_NAME_KEYWORD_2 = "Keyword_2";
        public static final String COLOUMN_NAME_KEYWORD_2_ARRAY_PATTERN = "Keyword_2_Array_Pattern";
        public static final String TABLE_NAME = "Suggestion_Table";
    }

    private Contract() {
    }
}
