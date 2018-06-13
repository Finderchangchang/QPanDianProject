package q.pandian.ui.ac

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import gd.mmanage.method.Utils
import kotlinx.android.synthetic.main.activity_login.*
import q.pandian.R
import q.pandian.base.Url
import q.pandian.base.http.ListRequest
import q.pandian.base.ui.BaseActivity
import q.pandian.config.command
import q.pandian.control.MainModule
import q.pandian.databinding.ActivityLoginBinding
import q.pandian.model.UserModel
import q.pandian.model.VersionModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun onSuccess(result: Int, success: Any?) {
        try {
            var model = success as ListRequest<UserModel>
            when (result) {
                command.login + 1 -> {
                    if (model.state == 1) {
                        var user = model.data?.get(0) as UserModel;
                        Utils.putCache(Url.u_name, user.userName)
                        Utils.putCache(Url.u_id, user.id)
                        Utils.putCache(Url.u_shopid, user.branchcode)
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        toast(model.msg)
                    }
                }
                command.login + 2 -> {
//更新
                }
            }
        } catch (e: Exception) {
            onError(result, success)
        }

    }

    private var control: MainModule? = null
    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        control = getModule(MainModule::class.java, this)
        //control!!.check_version()//检查更新操作

        var namestr = Utils.getCache(Url.u_name)
        var uid = Utils.getCache(Url.u_id)
        if (!(namestr.equals("") && uid.equals(""))) {

            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        login_btn.setOnClickListener {
            var name = name_et.text.toString().trim()
            var pwd = pwd_et.text.toString().trim()
            when {
                TextUtils.isEmpty(name) -> toast(getString(R.string.name_is_error))
                TextUtils.isEmpty(pwd) -> toast(getString(R.string.pwd_is_error))
                else -> control!!.login(name, pwd)//登录
            }
        }
    }


    override fun setLayoutId(): Int {
        return R.layout.activity_login
    }
}
