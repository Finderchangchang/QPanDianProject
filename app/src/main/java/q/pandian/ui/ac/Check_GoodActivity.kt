package q.pandian.ui.ac

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

/**
 * 盘点列表页面
 * */
class Check_GoodActivity : BaseActivity<ActivityCheckGoodBinding>() {
    var list=ArrayList<GoodModel>()
    var adapter:CommonAdapter<GoodModel>?=null
    override fun onSuccess(result: Int, success: Any?) {
        try {
            var model = success as ListRequest<GoodModel>
            when (result) {
                command.good + 1 -> {
                    if(model.state==1){
                        list= model.data as ArrayList<GoodModel>
                        adapter?.refresh(list)
                    }else{
                        toast(model.msg)
                    }
                }
                command.login + 2 -> {

                }
            }
        }catch (e:Exception){
            onError(result,success)
        }

    }
    private var control: GoodModule? = null
    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        control = getModule(GoodModule::class.java, this)
        control?.search_goods()
        adapter=object :CommonAdapter<GoodModel>(this,list as MutableList<GoodModel>,R.layout.item_good){
            override fun convert(holder: CommonViewHolder?, t: GoodModel?, position: Int) {
                holder?.setText(R.id.good_id_tv, t?.barcode)
                holder?.setText(R.id.good_name_tv, t?.shopName)
                holder?.setText(R.id.hj1_et, t?.numberOneDesk)
                holder?.setText(R.id.hj2_et, t?.numberOneFloor)
                holder?.setText(R.id.hj3_et, t?.numberTowFloor)
                holder?.setText(R.id.beizhu_et, t?.beizhu)

            }
        }
        srl.setOnRefreshListener {
            control?.search_goods()
            srl.isRefreshing=false
        }
    }
    override fun setLayoutId(): Int {
        return R.layout.activity_check__good
    }

}
