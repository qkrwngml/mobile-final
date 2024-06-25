package dduw.com.mobile.finalreport

import android.content.ContentValues
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dduw.com.mobile.finalreport.data.DiaryDBHelper
import dduw.com.mobile.finalreport.data.DiaryDto
import dduw.com.mobile.finalreport.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    val TAG = "AddActivity"

    lateinit var addBinding : ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        /*추가버튼 클릭 시*/
        addBinding.btnAddDiary.setOnClickListener{
            if (addBinding.etAddDiary.text.toString().equals("")){      //제목 필수 입력 확인
                Toast.makeText(this@AddActivity, "제목을 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                val title = addBinding.etAddDiary.text.toString()
                var date = addBinding.etAddDate.year.toString() + "/" + (addBinding.etAddDate.month + 1).toString() + "/" + addBinding.etAddDate.dayOfMonth.toString()
                val weather = addBinding.etAddWeather.text.toString()
                val emotion = addBinding.etAddEmotion.text.toString()
                val story = addBinding.etAddContent.text.toString()
                val newDto = DiaryDto(0, title, date, weather, emotion, story)      // 화면 값으로 dto 생성, id 는 임의의 값 0

                if (addDiary(newDto) > 0) {
                    Toast.makeText(this@AddActivity, "추가되었습니다.", Toast.LENGTH_SHORT).show()
                    setResult(AppCompatActivity.RESULT_OK)
                } else {
                    setResult(AppCompatActivity.RESULT_CANCELED)
                }
                finish()
            }
        }

        /*취소버튼 클릭 시*/
        addBinding.btnAddCancel.setOnClickListener{
            setResult(AppCompatActivity.RESULT_CANCELED)
            finish()
        }
    }


    /*추가할 정보를 담고 있는 dto 를 전달받아 DB에 추가, id 는 autoincrement 이므로 제외
    * DB추가 시 추가한 항목의 ID 값 반환, 추가 실패 시 -1 반환 */
    fun addDiary(newDto : DiaryDto) : Long  {
        val helper = DiaryDBHelper(this)
        val db = helper.writableDatabase

        val newValues = ContentValues()
        newValues.put(DiaryDBHelper.COL_TITLE, newDto.title)
        newValues.put(DiaryDBHelper.COL_DATE, newDto.date)
        newValues.put(DiaryDBHelper.COL_WEATHER, newDto.weather)
        newValues.put(DiaryDBHelper.COL_EMOTION, newDto.emotion)
        newValues.put(DiaryDBHelper.COL_STORY, newDto.story)

        /*insert 한 항목의 id 를 반환*/
        val result = db.insert(DiaryDBHelper.TABLE_NAME, null, newValues)

        helper.close()      // DB 작업 후 close()

        return result
    }

}