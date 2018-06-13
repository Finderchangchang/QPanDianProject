package q.pandian.model

import java.io.Serializable

/**
 * 商品类
 * {id 商品编号,shopNum 商品号,shopName 商品名,
 * barcode 条形码,format 规格,addtime 盘点开始时间，
 * numberCount 盘点前库存,numberSale 盘点间出售,
 * numberOneDesk 一楼货架库存,numberOneFloor 一楼仓库库存,
 * numberTowFloor 二楼库存,num 差异数量,beizhu 原因,boxnum 每件个数,
 * barcodeS 捆绑商品条码}
 * */
class GoodModel :Serializable{
   // id 商品编号,shopNum 商品号,shopName 商品名,barcode 条形码,
   // format 规格,addtime 盘点开始时间，numberCount 盘点前库存,
   // numberSale 盘点间出售,numberOneDesk 一楼货架库存,
   // numberOneFloor 一楼仓库库存,numberTowFloor 二楼库存,
    //num 差异数量,beizhu 原因,boxnum 每件个数,barcodeS 捆绑商品条码
    var id=""
    var shopNum=""
    var shopName=""
    var barcode=""
    var format=""
    var addtime=""
    var numberCount=""
    var numberSale=""
    var numberOneDesk=""
    var numberOneFloor=""
    var numberTowFloor=""
    var num=""
    var beizhu=""
    var boxnum=""
    var barcodeS=""
}
