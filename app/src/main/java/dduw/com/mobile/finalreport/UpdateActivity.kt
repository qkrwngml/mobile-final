package dduw.com.mobile.finalreport

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dduw.com.mobile.finalreport.data.DiaryDBHelper
import dduw.com.mobile.finalreport.data.DiaryDto
import dduw.com.mobile.finalreport.databinding.ActivityAddBinding
import dduw.com.mobile.finalreport.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    lateinit var updateBinding : ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        /*RecyclerView 에서 선택하여 전달한 dto 를 확인*/
        val dto = intent.getSerializableExtra("dto") as DiaryDto

        /*전달받은 값으로 화면에 표시*/

        var dateStr = dto.date.split("/")       //date 용
        var idx = dto.id        //이미지 삽입용
        var idxImg = R.mipmap.icons01
        when (idx%6) {
            0 -> idxImg = R.mipmap.icons01
            1 -> idxImg = R.mipmap.icons02
            2 -> idxImg = R.mipmap.icons03
            3 -> idxImg = R.mipmap.icons04
            4 -> idxImg = R.mipmap.icons05
            5 -> idxImg = R.mipmap.icons06
        }
        updateBinding.ivUpdatePhoto.setImageResource(idxImg)
        updateBinding.etUpdateId.setText(dto.id.toString())
        updateBinding.etUpdateTitle.setText(dto.title)
        updateBinding.etUpdateDate.updateDate(dateStr[0].toInt(), (dateStr[1].toInt() - 1), dateStr[2].toInt())
        updateBinding.etUpdateWeather.setText(dto.weather)
        updateBinding.etUpdateEmotion.setText(dto.emotion)
        updateBinding.etUpdateContent.setText(dto.story)

        updateBinding.btnUpdateDiary.setOnClickListener{

            if (updateBinding.etUpdateTitle.text.toString().equals("")){      // 제목 필수 입력 확인
                Toast.makeText(this@UpdateActivity, "제목을 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                /*dto 는 아래와 같이 기존의 dto 를 재사용할 수도 있음*/
                dto.title = updateBinding.etUpdateTitle.text.toString()
                dto.date = updateBinding.etUpdateDate.year.toString() + "/" + (updateBinding.etUpdateDate.month + 1).toString() + "/" + updateBinding.etUpdateDate.dayOfMonth.toString()
                dto.weather = updateBinding.etUpdateWeather.text.toString()
                dto.emotion = updateBinding.etUpdateEmotion.text.toString()
                dto.story = updateBinding.etUpdateContent.text.toString()

                if (updateDiary(dto) > 0) {
                    Toast.makeText(this@UpdateActivity, "수정되었습니다.", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)      // update 를 수행했으므로 RESULT_OK 를 반환
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


    /*update 정보를 담고 있는 dto 를 전달 받아 dto 의 id 를 기준으로 수정*/
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

        /*upate 가 적용된 레코드의 개수 반환*/
        val result =  db.update(DiaryDBHelper.TABLE_NAME,
            updateValue, whereCaluse, whereArgs)

        helper.close()      // DB 작업 후에는 close()

        return result
    }


}