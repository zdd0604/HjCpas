package com.hj.casps.entity.appContract;

import com.hj.casps.protocolmanager.OrderRowBean;

import java.util.List;

//返回的参数，此参数是订单管理的参数
public class OrderBackList {

    /**
     * return_code : 0
     * return_message : success
     * rows : [{"buyersAddressId":"f9a6aaa8380a4ee9a5ad59fe81868361","buyersAddressName":"奥森学校中心食堂冷库","buyersName":"奥森学校","createTime":1495105852000,"executeEndTime":1495036800000,"executeStartTime":1495036800000,"executeStatus":1,"getAccount":"6222189929039864012","getBank":"中国建设银行","id":"5fb3e707307c4d379319d9c4ff2661ba","operateTime":1495105852000,"ordertitleCode":10000405,"payAccount":"9953001798001","payBank":"建设银行","payTime":1495036800000,"sellersAddressId":"7633cc6dd2334c8fa1090962b26a4624","sellersAddressName":"哦破rosy送","sellersName":"长城商行","status":3,"stopStatus":1,"totalMoney":3246,"userId":"475814ffe832455dba6ecdf4306b3642","userName":"1212","workflowTypeId":3},{"buyersAddressName":"","buyersId":"testshop001","buyersName":"长城商行","createTime":1495094735000,"executeEndTime":1495036800000,"executeStartTime":1495036800000,"executeStatus":1,"getBank":"","id":"6e0cff206b4c4d218b66db96aedc0511","operateTime":1495094735000,"ordertitleCode":10000308,"payBank":"","sellersAddressName":"","sellersId":"da4383de72494f5d98dc7836d25f526f","sellersName":"cyh","status":2,"stopStatus":1,"totalMoney":30000,"userId":"945fa151f0ce4dc6986ddd13728af39d","userName":"全功能","workflowTypeId":4},{"buyersAddressName":"","buyersId":"testschool001","buyersName":"奥森学校","createTime":1495019702000,"executeEndTime":1494950400000,"executeStartTime":1494950400000,"executeStatus":2,"finishTime":1495160276000,"getAccount":"6222189929039864012","getBank":"中国建设银行","id":"1d43817f78044b369987059d5174d143","lockTime":1495019974000,"operateTime":1495160276000,"ordertitleCode":10000302,"payBank":"","payTime":1495036800000,"sellersAddressId":"7833d058979249ce91028fe174af271e","sellersAddressName":"自摸嗖嗖嗖","sellersId":"testshop001","sellersName":"长城商行","status":4,"stopStatus":4,"totalMoney":400,"userId":"ad4410e4864a41ccbd2b6b4b4914b715","userName":"奥森主管","workflowTypeId":2},{"buyersAddressId":"388f41ee44b04aeea3640140f91f380e","buyersAddressName":"Beijing","buyersName":"³¤³ÇÉÌÐÐ","createTime":1495011940000,"executeEndTime":1494864000000,"executeStartTime":1494864000000,"executeStatus":1,"getAccount":"","getBank":"","id":"621d0f2516414bf484ba5d979f40742e","operateTime":1495011940000,"ordertitleCode":10099000,"payAccount":"123214453454","payBank":"ÖÐ¹úÒøÐÐ","payTime":1494864000000,"sellersAddressId":"","sellersAddressName":"","sellersName":"Áõ³¤³Ç","status":2,"stopStatus":1,"totalMoney":15996,"userId":"a29d2326763546a4b0063c202cff08ff","workflowTypeId":3},{"buyersAddressName":"","buyersId":"testschool001","buyersName":"奥森学校","createTime":1494994281000,"executeEndTime":1494950400000,"executeStartTime":1494950400000,"executeStatus":1,"getAccount":"6222189929039864012","getBank":"中国建设银行","id":"12bafd55844a4f82acdd13984c830214","operateTime":1494994281000,"ordertitleCode":10000301,"payBank":"","payTime":1494950400000,"sellersAddressId":"7833d058979249ce91028fe174af271e","sellersAddressName":"自摸嗖嗖嗖","sellersId":"testshop001","sellersName":"长城商行","status":3,"stopStatus":1,"totalMoney":4000,"userId":"94c108267af94624bcf99171690741cf","userName":"全功能测试","workflowTypeId":3},{"buyersAddressName":"","buyersId":"da4383de72494f5d98dc7836d25f526f","buyersName":"cyh","createTime":1494994179000,"executeEndTime":1494950400000,"executeStartTime":1494950400000,"executeStatus":1,"getAccount":"6222189929039864012","getBank":"中国建设银行","id":"805079325749452499d48a22984d7c01","operateTime":1494994179000,"ordertitleCode":10000300,"payBank":"","payTime":1494950400000,"sellersAddressId":"78d5b989f2f3436f9b871a4756ac0657","sellersAddressName":"我可以","sellersId":"testshop001","sellersName":"长城商行","status":3,"stopStatus":1,"totalMoney":10000,"userId":"94c108267af94624bcf99171690741cf","userName":"全功能测试","workflowTypeId":2},{"buyersAddressId":"388f41ee44b04aeea3640140f91f380e","buyersAddressName":"Beijing","buyersName":"³¤³ÇÉÌÐÐ","createTime":1494980437000,"executeEndTime":1494864000000,"executeStartTime":1494864000000,"executeStatus":1,"getAccount":"","getBank":"","id":"9e69d04389354459af1751ce63190b3e","operateTime":1494980437000,"ordertitleCode":10098900,"payAccount":"123214453454","payBank":"ÖÐ¹úÒøÐÐ","payTime":1494864000000,"sellersAddressId":"","sellersAddressName":"","sellersName":"Áõ³¤³Ç","status":2,"stopStatus":1,"totalMoney":15996,"userId":"a29d2326763546a4b0063c202cff08ff","workflowTypeId":3},{"buyersAddressId":"adbc73908b5841e3bc61ff12780e9610","buyersAddressName":"","buyersId":"testschool001","buyersName":"奥森学校","createTime":1491898956000,"executeEndTime":1492185600000,"executeStartTime":1492185600000,"executeStatus":1,"getAccount":"68900055338","getBank":"","id":"514a1de67b104c688c000e9c84a15ec6","operateTime":1491898956000,"ordertitleCode":10000003,"payAccount":"9953001798001","payBank":"","payTime":1492012800000,"sellersAddressId":"d2f7b7029fb341879ba3b009376dd274","sellersAddressName":"","sellersId":"a844cb008f2643a4b5ec7d9fc664f1ce","sellersName":"北京尚德粮油商贸公司","status":2,"stopStatus":1,"totalMoney":130,"userId":"ad4410e4864a41ccbd2b6b4b4914b715","userName":"奥森主管","workflowTypeId":2},{"buyersAddressName":"","buyersId":"da4383de72494f5d98dc7836d25f526f","buyersName":"cyh","createTime":1491887871000,"executeEndTime":1492444800000,"executeStartTime":1492444800000,"executeStatus":1,"getAccount":"63980011596","getBank":"中信银行","id":"e9508ce21602448bab348c7464b6c5d4","operateTime":1491887871000,"ordertitleCode":10000002,"payBank":"","payTime":1492185600000,"sellersAddressId":"ff3bce69cc094a0ca3488e833c40e5bb","sellersAddressName":"北京市朝阳区立水桥5号库","sellersId":"testshop001","sellersName":"长城商行","status":3,"stopStatus":1,"totalMoney":24,"userId":"945fa151f0ce4dc6986ddd13728af39d","userName":"全功能","workflowTypeId":2},{"buyersAddressId":"f418e4c02c824b4b80862b2942bce6a4","buyersAddressName":"","buyersId":"72365baa7dd34594b890045cf41efed3","buyersName":"cyb","createTime":1491465920000,"executeEndTime":1491408000000,"executeStartTime":1491408000000,"executeStatus":2,"finishTime":1491470953000,"getAccount":"61272461165061","getBank":"","id":"f9ba1542f4584148a2cc187d82c292d2","lockTime":1491465987000,"operateTime":1491465987000,"ordertitleCode":10038640,"payAccount":"6154613216584","payBank":"","payTime":1491408000000,"sellersAddressId":"636b30c669cb4a8ba4ba0c8d88cf3224","sellersAddressName":"","sellersId":"da4383de72494f5d98dc7836d25f526f","sellersName":"cyh","status":4,"stopStatus":1,"totalMoney":4300,"userId":"be89786782e049a18f54f5730263f861","userName":"cyh","workflowTypeId":2}]
     * total : 157
     */

    private int return_code;
    private String return_message;
    private int total;
    private List<OrderRowBean> rows;

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
        this.return_code = return_code;
    }

    public String getReturn_message() {
        return return_message;
    }

    public void setReturn_message(String return_message) {
        this.return_message = return_message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OrderRowBean> getRows() {
        return rows;
    }

    public void setRows(List<OrderRowBean> rows) {
        this.rows = rows;
    }
}