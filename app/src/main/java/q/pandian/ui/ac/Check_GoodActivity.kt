package q.pandian.ui.ac

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import gd.mmanage.method.Utils
import kotlinx.android.synthetic.main.activity_check__good.*
import q.pandian.R
import q.pandian.base.Url
import q.pandian.base.http.ListRequest
import q.pandian.base.method.CommonAdapter
import q.pandian.base.method.CommonViewHolder
import q.pandian.base.ui.BaseActivity
import q.pandian.config.command
import q.pandian.control.GoodModule
import q.pandian.databinding.ActivityCheckGoodBinding
import q.pandian.model.GoodModel
import q.pandian.model.UserModel
import q.pandian.MainActivity
import com.github.shenyuanqing.zxingsimplify.zxing.Activity.CaptureActivity
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import q.pandian.base.http.ModelRequest


/**
 * 盘点列表页面
 * 1.盘点完成是单个Item
 * 2.保存盘点列表（保存所有的）
 * */
class Check_GoodActivity : BaseActivity<ActivityCheckGoodBinding>() {
    var list = ArrayList<GoodModel>()
    var num_list = ArrayList<Int>()
    var adapter: CommonAdapter<GoodModel>? = null
    var isRefush = false
    private fun sum(model: GoodModel): Int {
        var num = 0
        if (!TextUtils.isEmpty(model.numberOneDesk)) {
            num += model.numberOneDesk.toInt()
        }
        if (!TextUtils.isEmpty(model.numberOneFloor)) {
            num += model.numberOneFloor.toInt()
        }
        if (!TextUtils.isEmpty(model.numberTowFloor)) {
            num += model.numberTowFloor.toInt()
        }
        return num
    }

    override fun onSuccess(result: Int, success: Any?) {
        try {
            var model = success as ListRequest<GoodModel>
            when (result) {
                command.good + 1 -> {//列表数据
                    if (model.state == 1) {
                        list.removeAll(list)
                        list = model.data as ArrayList<GoodModel>
                        num_list.removeAll(num_list)
                        for (key in list) {
                            var num = 0
                            if (!TextUtils.isEmpty(key.numberSale)) {
                                num += key.numberSale.toInt()
                            }
                            if (!TextUtils.isEmpty(key.numberCount)) {
                                num += key.numberCount.toInt()
                            }
                            num_list.add(num)
                        }
                        adapter?.refresh(list)
                        if (isRefush) {
                            toast("数据已更新")
                            isRefush = false
                        }
                    } else {
                        list.removeAll(list)
                        adapter?.refresh(list)
                        toast(model.msg)
                    }
                }
                command.good + 2 -> {//扫码结果处理
                    if (model.state == 1) {
                        control?.search_goods()
                    } else {
                        toast(model.msg)
                    }
                }
            }
        } catch (e: Exception) {
            var model = success as ModelRequest<String>
            when (result) {
                command.good + 3 -> {//删除盘点商品
                    if (model.state == 1) {
                        toast("删除成功")
                        control?.search_goods()
                    } else {
                        toast(model.msg)
                    }
                }
                command.good + 4 -> {//保存盘点列表
                    if (model.state == 1) {
                        toast("保存成功")
                        control?.search_goods()
                    } else {
                        toast("请重新扫码")
                    }
                }
                command.good + 5 -> {//盘点完成
                    if (model.state == 1) {
                        toast("盘点完成")
                        control?.search_goods()
                    } else {
                        toast("请重新扫码")
                    }
                }
                command.good + 1 -> {
                    if (model.state == 1) {
                        toast("成功")
                        list.removeAll(list)
                        adapter?.refresh(list)
                    } else {
                        list.removeAll(list)
                        adapter?.refresh(list)
                        toast(model.msg)
                    }

                }
                command.good + 2 -> {
                    if (model.state == 1) {
                        control?.search_goods()
                        toast("成功")
                    } else {
                        toast(model.msg)
                    }
                }

            }
        }

    }

    companion object {
        var main: Check_GoodActivity? = null
    }

    private var control: GoodModule? = null
    var positon = 0;
    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        main = this
        control = getModule(GoodModule::class.java, this)
        title_bar.setRightClick {
            getRuntimeRight()
        }
        control?.search_goods()

        adapter = object : CommonAdapter<GoodModel>(this, list as MutableList<GoodModel>, R.layout.item_good) {
            @SuppressLint("ClickableViewAccessibility")
            override fun convert(holder: CommonViewHolder?, t: GoodModel?, position: Int) {
                holder?.setText(R.id.good_id_tv, t?.barcode)
                holder?.setText(R.id.good_name_tv, t?.shopName)
                holder?.setHintText(R.id.hj1_et, t?.numberOneDesk)
                holder?.setHintText(R.id.hj2_et, t?.numberOneFloor)
                holder?.setHintText(R.id.hj3_et, t?.numberTowFloor)
                holder?.setHintText(R.id.beizhu_et, t?.beizhu)
                holder?.setText(R.id.item_good_num, t?.num)
                holder?.setTextWatcher(R.id.beizhu_et, object : TextWatcher {
                    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun afterTextChanged(editable: Editable) {
                        list[position].beizhu = holder?.getText(R.id.beizhu_et)
                    }
                })
                holder?.setTextWatcher(R.id.hj1_et, object : TextWatcher {
                    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun afterTextChanged(editable: Editable) {
                        var txt=holder.getText(R.id.hj1_et)
                        if(TextUtils.isEmpty(txt)){
                            list[position].numberOneDesk ="0"
                        }else{
                            list[position].numberOneDesk =txt
                        }
                        holder.setText(R.id.item_good_num, chayi(list[position], num_list[position]).toString())
                    }
                })
                holder?.setTextWatcher(R.id.hj2_et, object : TextWatcher {
                    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun afterTextChanged(editable: Editable) {
                        var txt=holder.getText(R.id.hj2_et)
                        if(TextUtils.isEmpty(txt)){
                            list[position].numberOneFloor ="0"
                        }else{
                            list[position].numberOneFloor =txt
                        }
                        holder.setText(R.id.item_good_num, chayi(list[position], num_list[position]).toString())
                    }
                })
                holder?.setTextWatcher(R.id.hj3_et, object : TextWatcher {
                    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun afterTextChanged(editable: Editable) {
                        var txt=holder.getText(R.id.hj3_et)
                        if(TextUtils.isEmpty(txt)){
                            list[position].numberTowFloor ="0"
                        }else{
                            list[position].numberTowFloor =txt
                        }
                        holder.setText(R.id.item_good_num, chayi(list[position], num_list[position]).toString())
                    }
                })
                //删除
                holder?.setOnClickListener(R.id.del_btn) {
                    builder = AlertDialog.Builder(main!!).setTitle("确定要删除当前数据吗？")

                    builder.setPositiveButton("确定") { a, b ->
                        control?.delete_good(t?.id + "")
                    }
                    builder.setNegativeButton("关闭") { a, b ->

                    }
                    builder.show()
                }
                //完成盘点（单条数据）
                holder?.setOnClickListener(R.id.pandian_btn) {
                    var list = ArrayList<GoodModel>()
                    list.add(t!!)
                    var gson = Gson().toJson(list)
                    control?.scan_success(gson)
                }
                //详情
                holder?.setOnClickListener(R.id.detail_btn) {
                    startActivity(Intent(Check_GoodActivity.main, GoodDetailActivity::class.java).putExtra("goodmodel", t))
                }
            }
        }
        main_lv.adapter = adapter
//        main_lv.setOnItemClickListener{a,b,c,d->
//            var model=list[c]
//            startActivity(Intent(this, GoodDetailActivity::class.java).putExtra("goodmodel",model))
//        }
        srl.setOnRefreshListener {
            isRefush = true
            control?.search_goods()
            srl.isRefreshing = false
        }
        check_good_refush.setOnClickListener {
            isRefush = true
            control?.search_goods()
            //srl.isRefreshing = false
        }
        check_good_save.setOnClickListener {
            var gson = Gson().toJson(list)
            control?.save_goods(gson)
        }
    }

    fun chayi(now_good: GoodModel, num: Int): Int {
        var nums = sum(now_good) - num
        return nums
    }

    /**
     * 获得运行时权限
     */
    private fun getRuntimeRight() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.CAMERA), 1)
        } else {
            jumpScanPage()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                jumpScanPage()
            } else {
                Toast.makeText(this, "拒绝", Toast.LENGTH_LONG).show()
            }
            else -> {
            }
        }
    }

    var REQUEST_SCAN = 10000
    /**
     * 跳转到扫码页
     */
    private fun jumpScanPage() {
        startActivityForResult(Intent(this, CaptureActivity::class.java), REQUEST_SCAN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SCAN && resultCode == Activity.RESULT_OK) {
            control?.scan_good(data!!.getStringExtra("barCode"))
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_check__good
    }

}
