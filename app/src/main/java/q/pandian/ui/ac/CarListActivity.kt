package q.pandian.ui.ac

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.activity_car_list.*
import q.pandian.R
import q.pandian.base.method.CommonAdapter
import q.pandian.base.method.CommonViewHolder


class CarListActivity : AppCompatActivity() {
    var adapter: CommonAdapter<String>? = null
    var list = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_list)
        adapter = object : CommonAdapter<String>(this, list as MutableList<String>?, R.layout.item_car) {
            override fun convert(holder: CommonViewHolder?, t: String?, position: Int) {
                holder?.setText(R.id.txt, t)
            }
        }
        main_lv.adapter = adapter
        car_et.requestFocus();
        car_et.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.keyCode && KeyEvent.ACTION_DOWN == event.getAction())) {
                //处理事件
                var num = car_et.text.toString().trim()
                if (!num.isEmpty()) {
                    car_et.setText("")
                    if (!list.contains(num)) {
                        list.add(num)
                        adapter?.refresh(list)
                        car_et.requestFocus();
                    }
                }
            }
            true
        }
    }
}
