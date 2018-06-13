package q.pandian.ui.ac

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import gd.mmanage.method.Utils
import kotlinx.android.synthetic.main.activity_home.*
import q.pandian.R
import q.pandian.base.Url
import q.pandian.base.ui.BaseActivity
import q.pandian.databinding.ActivityHomeBinding
/**
 * 首页
 * */
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)

        home_name.setText(Utils.getCache(Url.u_name));
        //执行扫码操作
        scan_btn.setOnClickListener{
            startActivity(Intent(this,Check_GoodActivity::class.java))
        }
        home_exit.setOnClickListener{
            builder=null
            builder = AlertDialog.Builder(this!!).setTitle("确定要退出吗？")

            builder.setPositiveButton("确定") { a, b ->

                Utils.putCache(Url.u_name,"")
                Utils.putCache(Url.u_id, "")
                Utils.putCache(Url.u_shopid,"")
                finish()
            }
            builder.setNegativeButton("关闭") { a, b ->

            }
            builder.show()
        }
    }
    override fun setLayoutId(): Int {
        return R.layout.activity_home
    }
}
