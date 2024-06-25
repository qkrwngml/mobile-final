package dduw.com.mobile.finalreport.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class DiaryDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 2) {
    val TAG = "DiaryDBHelper"

    companion object {
        const val DB_NAME = "diary_db"
        const val TABLE_NAME = "diary_table"
        const val COL_TITLE = "title"
        const val COL_DATE = "date"
        const val COL_WEATHER = "weather"
        const val COL_EMOTION = "emotion"
        const val COL_STORY = "content"
    }
    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_TITLE TEXT, $COL_DATE TEXT, $COL_WEATHER TEXT, $COL_EMOTION TEXT, $COL_STORY TEXT )"
        Log.d(TAG, CREATE_TABLE)
        db?.execSQL(CREATE_TABLE)

        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '첫번째 기록', '2024/01/01', '맑음', '좋음', '첫번째 내용')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '두번째 기록', '2024/02/01', '흐림', '슬픔', '두번째 내용')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '세번째 기록', '2024/03/01', '비', '행복', '세번째 내용')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '네번째 기록', '2024/04/01', '맑음', '신남', '네번째 내용')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '다섯번째 기록', '2024/05/01', '맑음', '좋음', '다섯번째 내용')")


    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}