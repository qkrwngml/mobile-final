package dduw.com.mobile.finalreport.data

class DiaryDao {
    private val diaries = ArrayList<DiaryDto>()

    init {
        diaries.add( DiaryDto(1, "첫번째 기록", "2024/01/01", "맑음", "좋음", "첫번째 내용"))
        diaries.add( DiaryDto(2, "두번째 기록", "2024/02/01", "흐림", "슬픔", "두번째 내용"))
        diaries.add( DiaryDto(3, "세번째 기록", "2024/03/01", "비", "행복", "세번째 내용"))
        diaries.add( DiaryDto(4, "네번째 기록", "2024/04/01", "맑음", "신남", "네번째 내용"))
        diaries.add( DiaryDto(5, "다섯번째 기록", "2024/05/01", "맑음", "좋음", "다섯번째 내용"))
    }

    fun getAllDiarys() : ArrayList<DiaryDto> {
        return diaries
    }
    fun addNewDiary(newDiaryDto : DiaryDto) {
        diaries.add(newDiaryDto)
    }
    fun modifyDiary(pos: Int, modifyDiaryDto : DiaryDto) {
        diaries.set(pos, modifyDiaryDto)
    }
    fun removeDiary(removeDiaryDto : DiaryDto) {
        val index = diaries.indexOf(removeDiaryDto)
        diaries.removeAt(index)
    }
    fun getDiaryByPos(pos : Int) = diaries.get(pos)
}