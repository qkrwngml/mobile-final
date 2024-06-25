package dduw.com.mobile.finalreport

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dduw.com.mobile.finalreport.data.DiaryDBHelper
import dduw.com.mobile.finalreport.data.DiaryDto
import dduw.com.mobile.finalreport.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    lateinit var updateBinding : ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        val dto = intent.getSerializableExtra("dto") as DiaryDto

        var dateStr = dto.date.split("/")
        var idx = dto.id
        var idxImg = R.mipmap.icons01
        when (idx%6) {
            1 -> idxImg = R.mipmap.icons01
            2 -> idxImg = R.mipmap.icons02
            3 -> idxImg = R.mipmap.icons03
            4 -> idxImg = R.mipmap.icons04
            5 -> idxImg = R.mipmap.icons05
        }

        updateBinding.etUpdateId.setText(dto.id.toString())
        updateBinding.etUpdateTitle.setText(dto.title)
        updateBinding.etUpdateDate.updateDate(dateStr[0].toInt(), (dateStr[1].toInt() - 1), dateStr[2].toInt())
        updateBinding.etUpdateWeather.setText(dto.weather)
        updateBinding.etUpdateEmotion.setText(dto.emotion)
        updateBinding.etUpdateContent.setText(dto.story)

        updateBinding.btnUpdateDiary.setOnClickListener{

            if (updateBinding.etUpdateTitle.text.toString().equals("")){
                Toast.makeText(this@UpdateActivity, "제목을 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                dto.title = updateBinding.etUpdateTitle.text.toString()
                dto.date = updateBinding.etUpdateDate.year.toString() + "/" + (updateBinding.etUpdateDate.month + 1).toString() + "/" + updateBinding.etUpdateDate.dayOfMonth.toString()
                dto.weather = updateBinding.etUpdateWeather.text.toString()
                dto.emotion = updateBinding.etUpdateEmotion.text.toString()
                dto.story = updateBinding.etUpdateContent.text.toString()

                if (updateDiary(dto) > 0) {
                    Toast.makeText(this@UpdateActivity, "수정되었습니다.", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                } else {
                    setResult(RESULT_CANCELED)
                }
                finish()
            }
        }
        updateBinding.btnUpdateCancel.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    fun updateDiary(dto: DiaryDto): Int {
        val helper = DiaryDBHelper(this)
        val db = helper.writableDatabase
        val updateValue = ContentValues()
        updateValue.put(DiaryDBHelper.COL_TITLE, dto.title)
        updateValue.put(DiaryDBHelper.COL_DATE, dto.date)
        updateValue.put(DiaryDBHelper.COL_WEATHER, dto.weather)
        updateValue.put(DiaryDBHelper.COL_EMOTION, dto.emotion)
        updateValue.put(DiaryDBHelper.COL_STORY, dto.story)
        val whereCaluse = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        val result =  db.update(DiaryDBHelper.TABLE_NAME,
            updateValue, whereCaluse, whereArgs)

        helper.close()
        return result
    }


}