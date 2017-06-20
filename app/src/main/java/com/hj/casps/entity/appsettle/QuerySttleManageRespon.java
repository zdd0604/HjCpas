package com.hj.casps.entity.appsettle;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/18.
 * 执行中的结款单列表
 * 结款单登记担保列表
 */

public class QuerySttleManageRespon<T> implements Serializable {
    private static final long serialVersionUID = 2192012375406459889L;
    /**
     * list : [{"ctrMoney":12030,"ctrTime":1483113600000,"id":"2a7cc52e0ca84e9b9eff563088768f26","mmbgetName":"长城商行","mmbpayName":"奥森学校","settleCode":40036902,"settleMoney":12030,"status":4},{"ctrMoney":1740,"ctrTime":1483113600000,"id":"4515b4be40464abbbd55a23a7bb0fa6f","mmbgetName":"miao","mmbpayName":"奥森学校","settleCode":40036710,"settleMoney":1740,"status":4},{"ctrMoney":864,"ctrTime":1480435200000,"id":"6732335dbe7740bfbd1b25893c4b1fff","mmbgetName":"长城商行","mmbpayName":"奥森学校","settleCode":40036725,"settleMoney":864,"status":4},{"ctrMoney":450,"ctrTime":1493568000000,"id":"8450d4149c8341668ac4213779889c44","mmbgetName":"长城商行","mmbpayName":"奥森学校","settleCode":40038409,"settleMoney":480,"status":4},{"ctrMoney":560,"ctrTime":1498752000000,"id":"9ea0c7fe0ffb440aae601d2cb24737d2","mmbgetName":"长城商行","mmbpayName":"奥森学校","settleCode":40038429,"settleMoney":560,"status":4},{"ctrMoney":70,"ctrTime":1493568000000,"id":"a09f2e899fed4ad092bc75659dd49655","mmbgetName":"长城商行","mmbpayName":"奥森学校","settleCode":40038417,"settleMoney":75,"status":4},{"ctrMoney":870,"ctrTime":1500998400000,"id":"a1a6e5686c2c4d46b5d0c7e36cd36079","mmbgetName":"长城商行","mmbpayName":"奥森学校","settleCode":40038428,"settleMoney":870,"status":4},{"ctrMoney":4040,"ctrTime":1488988800000,"id":"b09a1509a7af4bcdbbeb5465018d8ee0","mmbgetName":"北京尚德粮油商贸公司","mmbpayName":"奥森学校","settleCode":40038439,"settleMoney":4040,"status":4},{"ctrMoney":5755,"ctrTime":1490889600000,"id":"b20f405c348e402a91a123ac61ffc374","mmbgetName":"长城商行","mmbpayName":"奥森学校","settleCode":40038445,"settleMoney":5755,"status":4},{"ctrMoney":3600,"ctrTime":1475164800000,"id":"cda0e2807ba746cd90682a9746f94f0d","mmbgetName":"长城商行","mmbpayName":"奥森学校","settleCode":40022003,"settleMoney":3600,"status":4}]
     * pagecount : 13
     * return_code : 0
     * return_message : success
     */

    public int pagecount;
    public int return_code;
    public String return_message;
    public T list;
}
