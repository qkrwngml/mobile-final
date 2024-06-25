package dduw.com.mobile.finalreport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.provider.BaseColumns
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import dduw.com.mobile.finalreport.data.DiaryDBHelper
import dduw.com.mobile.finalreport.data.DiaryDto
import dduw.com.mobile.finalreport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val REQ_ADD = 100
    val REQ_UPDATE = 200

    lateinit var binding : ActivityMainBinding
    lateinit var adapter : DiaryAdapter
    lateinit var diarys : ArrayList<DiaryDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDiarys.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        diarys = getAllDiarys()
        adapter = DiaryAdapter(diarys)
        binding.rvDiarys.adapter = adapter

        val onClickListener = object : DiaryAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", diarys.get(position) )
                startActivityForResult(intent, REQ_UPDATE)
            }
        }
        adapter.setOnItemClickListener(onClickListener)

        val onLongClickListener = object: DiaryAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                AlertDialog.Builder(this@MainActivity).run {
                    setTitle("다이어리 삭제")
                    setMessage("${diarys.get(position).title} 삭제하시겠습니까?")
                    setPositiveButton("확인", object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            if ( deleteDiary(diarys.get(position).id) > 0) {
                                refreshList(RESULT_OK)
                                Toast.makeText(this@MainActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                    setNegativeButton("취소", null)
                    show()
                }
            }
        }
        adapter.setOnItemLongClickListener(onLongClickListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_UPDATE -> {
                refreshList(resultCode)
            }
            REQ_ADD -> {
                refreshList(resultCode)
            }
        }
    }

    private fun refreshList(resultCode: Int) {
        if (resultCode == RESULT_OK) {
            diarys.clear()
            diarys.addAll(getAllDiarys())
            adapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this@MainActivity, "취소되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllDiarys() : ArrayList<DiaryDto> {
        val helper = DiaryDBHelper(this)
        val db = helper.readableDatabase
        val cursor = db.query(DiaryDBHelper.TABLE_NAME, null, null, null, null, null, null)

        val diarys = arrayListOf<DiaryDto>()
        try {
            cursor?.apply {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                    val title = getString(getColumnIndexOrThrow(DiaryDBHelper.COL_TITLE))
                    val date = getString(getColumnIndexOrThrow(DiaryDBHelper.COL_DATE))
                    val weather = getString(getColumnIndexOrThrow(DiaryDBHelper.COL_WEATHER))
                    val emotion = getString(getColumnIndexOrThrow(DiaryDBHelper.COL_EMOTION))
                    val story = getString(getColumnIndexOrThrow(DiaryDBHelper.COL_STORY))
                    val dto = DiaryDto(id, title, date, weather, emotion, story)
                    diarys.add(dto)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error while fetching diary entries: ${e.message}")
        } finally {
            cursor?.close()
            helper.close()
        }
        return diarys
    }

    fun deleteDiary(id: Int) : Int {
        val helper = DiaryDBHelper(this)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(id.toString())

        val result = db.delete(DiaryDBHelper.TABLE_NAME, whereClause, whereArgs)

        helper.close()
        return result
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_id -> {
                val intent = Intent(this, AddActivity::class.java)
                startActivityForResult(intent, REQ_ADD)
            }
            R.id.dev_id -> {
                val intent = Intent(this@MainActivity, InfoActivity::class.java)
                startActivity(intent)
            }
            R.id.close_id -> {
                AlertDialog.Builder(this@MainActivity).run {
                    setTitle("앱 종료")
                    setMessage("앱을 종료하시겠습니까?")
                    setPositiveButton("종료", object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            finish()
                        }
                    })
                    setNegativeButton("취소", null)
                    show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}