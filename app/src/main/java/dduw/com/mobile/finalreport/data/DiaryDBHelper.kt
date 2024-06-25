package dduw.com.mobile.finalreport.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class DiaryDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
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

        val values = ContentValues()
        values.put(COL_TITLE, "첫번째 기록")
        values.put(COL_DATE, "2024/01/01")
        values.put(COL_WEATHER, "맑음")
        values.put(COL_EMOTION, "좋음")
        values.put(COL_STORY, "첫번째 내용")
        db?.insert(TABLE_NAME, null, values)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}