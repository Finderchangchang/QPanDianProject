package q.pandian.ui.ac

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_good_detail.*

import q.pandian.R
import q.pandian.base.ui.BaseActivity
import q.pandian.databinding.ActivityGoodDetailBinding
import q.pandian.model.GoodModel
//详情
class GoodDetailActivity : BaseActivity<ActivityGoodDetailBinding>() {
    private var model: GoodModel? = null
    override fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        model = intent.getSerializableExtra("goodmodel") as GoodModel
        if(model!=null){
            gd_id.setText("商品编号："+model?.id)
            gd_shopNum.setText("商品号："+model?.shopNum)
            gd_shopname.setText("商品名："+model?.shopName)
            gd_barcode.setText("条形码："+model?.barcode)
            gd_format.setText("规格："+model?.format)

            gd_addtime.setText("盘点开始时间："+model?.addtime)
            gd_numbercount.setText("盘点前库存："+model?.numberCount)


            gd_numbersale.setText("盘点间出售："+model?.numberSale)
            gd_numberonedesk.setText("一楼货架库存："+model?.numberOneDesk)
            gd_numberonefloor.setText("一楼仓库库存："+model?.numberOneFloor)
            gd_numbertowfloor.setText("二楼库存："+model?.numberTowFloor)
            gd_num.setText("差异数量："+model?.num)

            gd_beizhu.setText("原因："+model?.beizhu)
            gd_boxnum.setText("每件个数："+model?.boxnum)
            gd_barcodes.setText("捆绑商品条码："+model?.barcodeS)

        }else{
            toast("数据加载失败")
            finish()
        }

    }
    override fun setLayoutId(): Int {
        return R.layout.activity_good_detail
    }
}
