package q.pandian.ui.ac

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import q.pandian.R
import q.pandian.base.ui.BaseActivity
import q.pandian.databinding.ActivityHomeBinding
/**
 * 首页
 * */
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        //执行扫码操作
        scan_btn.setOnClickListener{
            startActivity(Intent(this,Check_GoodActivity::class.java))
        }
    }
    override fun setLayoutId(): Int {
        return R.layout.activity_home
    }
}
