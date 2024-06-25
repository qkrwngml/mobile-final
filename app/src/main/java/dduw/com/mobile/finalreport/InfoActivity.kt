package dduw.com.mobile.finalreport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dduw.com.mobile.finalreport.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity()  {
    lateinit var devBinding : ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        devBinding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(devBinding.root)

        /*버튼 클릭 시 MainActivity로 이동*/
        devBinding.backBtn.setOnClickListener{
            finish()
        }
    }
}