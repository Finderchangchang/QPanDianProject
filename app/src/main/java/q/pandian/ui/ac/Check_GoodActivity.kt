package q.pandian.ui.ac

import android.Manifest
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
import android.text.TextWatcher
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
    var adapter: CommonAdapter<GoodModel>? = null
    var isRefush = false
    override fun onSuccess(result: Int, success: Any?) {
        try {
            var model = success as ListRequest<GoodModel>
            when (result) {
                command.good + 1 -> {//列表数据
                    if (model.state == 1) {
                        list.removeAll(list)
                        list = model.data as ArrayList<GoodModel>

                        adapter?.refresh(list)
                        if (isRefush) {
                            toast("数据已更新")
                            isRefush = false
                        }
                    } else {

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
                    } else {
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
    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        main = this
        control = getModule(GoodModule::class.java, this)
        title_bar.setRightClick {
            getRuntimeRight()
        }
        control?.search_goods()
        adapter = object : CommonAdapter<GoodModel>(this, list as MutableList<GoodModel>, R.layout.item_good) {
            override fun convert(holder: CommonViewHolder?, t: GoodModel?, position: Int) {
                holder?.setText(R.id.good_id_tv, t?.barcode)
                holder?.setText(R.id.good_name_tv, t?.shopName)
                holder?.setText(R.id.hj1_et, t?.numberOneDesk)
                holder?.setText(R.id.hj2_et, t?.numberOneFloor)
                holder?.setText(R.id.hj3_et, t?.numberTowFloor)
                holder?.setText(R.id.beizhu_et, t?.beizhu)
                holder?.setText(R.id.item_good_num, t?.num)
                holder?.setTextWatcher(R.id.hj1_et, object : TextWatcher {
                    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun afterTextChanged(editable: Editable) {
                        list[position].numberOneDesk = holder?.getText(R.id.hj1_et)
                    }
                })
                holder?.setTextWatcher(R.id.hj2_et, object : TextWatcher {
                    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun afterTextChanged(editable: Editable) {
                        list[position].numberOneFloor = holder?.getText(R.id.hj2_et)
                    }
                })
                holder?.setTextWatcher(R.id.hj3_et, object : TextWatcher {
                    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                    }

                    override fun afterTextChanged(editable: Editable) {
                        list[position].numberTowFloor = holder?.getText(R.id.hj3_et)
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
