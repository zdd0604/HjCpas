package com.hj.casps.entity.appcontract;

import java.util.List;

/**
 * 跳转同意协议输入界面返回类
 */
public class ShowAgreeBack {

    /**
     * data : {"address_name":"北京京东大市场15号库","addresslist":[{"address":"o.懦弱需要","contact":"","id":"12d9f0fe901a472f902c4cd3641a1783","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":"","areaId":""},{"address":"图兔兔","contact":"","id":"5a573889d2d341aeb2b34a2ad6a78f3a","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":"bbhh"},{"address":"哦破rosy送","contact":"","id":"7633cc6dd2334c8fa1090962b26a4624","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"自摸嗖嗖嗖","contact":"","id":"7833d058979249ce91028fe174af271e","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"我可以","contact":"","id":"78d5b989f2f3436f9b871a4756ac0657","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"mmllkzkxkxnx","contact":"","id":"78ea5f4e49094ca3866c24b2968a9e95","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"321321","id":"7e454a9381d64abc8ba15a2504c79241","mmbId":"testshop001"},{"address":"gghvhjj","contact":"","id":"88ac98a2c2314d17806ab0af4efbfd7d","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"哦nowwww","contact":"","id":"8d3b9bd610f842a9b346854857a0662e","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"就咯啦咯啦","contact":"","id":"9aad3093e5114341a77958ef07e6a27d","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"图兔兔","contact":"","id":"a6136995f3c64117b5c4260e1994c162","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"jjjj","contact":"","id":"b9e0b7f4c35947919327cd27443c1c2c","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"nnnn","contact":"","id":"c260c9e3ad674a07b95571be0c9ab50d","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"1234","areaId":"","contact":"","id":"c8dbc362ce4c443dadd0d1d7d0a06981","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":"123213"},{"address":"Haerbin","contact":"","id":"df32d5d53e324e52b42bd3ee597a9f32","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"hefei","areaId":"14","contact":"","id":"df9d103526b0484f8957f998ae43551a","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"北京市海淀区中关村大街11号财富E世界A座12层","areaId":"10","contact":"李丽","id":"e043abcb33424c1e85bf88ec738ff269","mmbId":"testshop001","mobilephone":"18010480093","phone":"18010480093","zipcode":"100000"},{"address":"也破也我人陪外婆","contact":"","id":"e09f61a50b234ae2958aa00231c3dec5","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"主题","contact":"","id":"e1ddd94a1f4b4c3fb9f47c824ba15c82","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"您婆婆哦婆婆","contact":"","id":"f4078204f5cf4af59f2f60afabc0f707","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""}],"bank_accountno":"63980011596","banklist":[{"accountname":"123123","accountno":"12312","bankname":"123123","contact":"12312","id":"4d4c7214e64a4dc696de9d5edc168dfd","mmbId":"testshop001","mobilephone":"123","phone":"12312"},{"accountname":"高于","accountno":"6222189929039864012","bankname":"中国建设银行","contact":"北京","id":"fc992fffb07442339239a25c521b7ed5","mmbId":"testshop001","mobilephone":"","phone":"18022002299"}],"contract_id":"3add492288584c57901d60ad022c1965","contract_status":"7","contract_type":"2"}
     * return_code : 0
     * return_message : 成功!
     */

    private DataBean data;
    private int return_code;
    private String return_message;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * address_name : 北京京东大市场15号库
         * addresslist : [{"address":"o.懦弱需要","contact":"","id":"12d9f0fe901a472f902c4cd3641a1783","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"图兔兔","contact":"","id":"5a573889d2d341aeb2b34a2ad6a78f3a","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":"bbhh"},{"address":"哦破rosy送","contact":"","id":"7633cc6dd2334c8fa1090962b26a4624","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"自摸嗖嗖嗖","contact":"","id":"7833d058979249ce91028fe174af271e","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"我可以","contact":"","id":"78d5b989f2f3436f9b871a4756ac0657","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"mmllkzkxkxnx","contact":"","id":"78ea5f4e49094ca3866c24b2968a9e95","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"321321","id":"7e454a9381d64abc8ba15a2504c79241","mmbId":"testshop001"},{"address":"gghvhjj","contact":"","id":"88ac98a2c2314d17806ab0af4efbfd7d","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"哦nowwww","contact":"","id":"8d3b9bd610f842a9b346854857a0662e","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"就咯啦咯啦","contact":"","id":"9aad3093e5114341a77958ef07e6a27d","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"图兔兔","contact":"","id":"a6136995f3c64117b5c4260e1994c162","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"jjjj","contact":"","id":"b9e0b7f4c35947919327cd27443c1c2c","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"nnnn","contact":"","id":"c260c9e3ad674a07b95571be0c9ab50d","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"1234","areaId":"","contact":"","id":"c8dbc362ce4c443dadd0d1d7d0a06981","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":"123213"},{"address":"Haerbin","contact":"","id":"df32d5d53e324e52b42bd3ee597a9f32","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"hefei","areaId":"14","contact":"","id":"df9d103526b0484f8957f998ae43551a","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"北京市海淀区中关村大街11号财富E世界A座12层","areaId":"10","contact":"李丽","id":"e043abcb33424c1e85bf88ec738ff269","mmbId":"testshop001","mobilephone":"18010480093","phone":"18010480093","zipcode":"100000"},{"address":"也破也我人陪外婆","contact":"","id":"e09f61a50b234ae2958aa00231c3dec5","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"主题","contact":"","id":"e1ddd94a1f4b4c3fb9f47c824ba15c82","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""},{"address":"您婆婆哦婆婆","contact":"","id":"f4078204f5cf4af59f2f60afabc0f707","mmbId":"testshop001","mobilephone":"","phone":"","zipcode":""}]
         * bank_accountno : 63980011596
         * banklist : [{"accountname":"123123","accountno":"12312","bankname":"123123","contact":"12312","id":"4d4c7214e64a4dc696de9d5edc168dfd","mmbId":"testshop001","mobilephone":"123","phone":"12312"},{"accountname":"高于","accountno":"6222189929039864012","bankname":"中国建设银行","contact":"北京","id":"fc992fffb07442339239a25c521b7ed5","mmbId":"testshop001","mobilephone":"","phone":"18022002299"}]
         * contract_id : 3add492288584c57901d60ad022c1965
         * contract_status : 7
         * contract_type : 2
         */

        private String address_name;
        private String bank_accountno;
        private String contract_id;
        private String contract_status;
        private String contract_type;
        private List<AddresslistBean> addresslist;
        private List<BanklistBean> banklist;

        public String getAddress_name() {
            return address_name;
        }

        public void setAddress_name(String address_name) {
            this.address_name = address_name;
        }

        public String getBank_accountno() {
            return bank_accountno;
        }

        public void setBank_accountno(String bank_accountno) {
            this.bank_accountno = bank_accountno;
        }

        public String getContract_id() {
            return contract_id;
        }

        public void setContract_id(String contract_id) {
            this.contract_id = contract_id;
        }

        public String getContract_status() {
            return contract_status;
        }

        public void setContract_status(String contract_status) {
            this.contract_status = contract_status;
        }

        public String getContract_type() {
            return contract_type;
        }

        public void setContract_type(String contract_type) {
            this.contract_type = contract_type;
        }

        public List<AddresslistBean> getAddresslist() {
            return addresslist;
        }

        public void setAddresslist(List<AddresslistBean> addresslist) {
            this.addresslist = addresslist;
        }

        public List<BanklistBean> getBanklist() {
            return banklist;
        }

        public void setBanklist(List<BanklistBean> banklist) {
            this.banklist = banklist;
        }

        public static class AddresslistBean {
            /**
             * address : o.懦弱需要
             * contact :
             * id : 12d9f0fe901a472f902c4cd3641a1783
             * mmbId : testshop001
             * mobilephone :
             * phone :
             * zipcode :
             * areaId :
             */

            private String address;
            private String contact;
            private String id;
            private String mmbId;
            private String mobilephone;
            private String phone;
            private String zipcode;
            private String areaId;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMmbId() {
                return mmbId;
            }

            public void setMmbId(String mmbId) {
                this.mmbId = mmbId;
            }

            public String getMobilephone() {
                return mobilephone;
            }

            public void setMobilephone(String mobilephone) {
                this.mobilephone = mobilephone;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getZipcode() {
                return zipcode;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }
        }

        public static class BanklistBean {
            /**
             * accountname : 123123
             * accountno : 12312
             * bankname : 123123
             * contact : 12312
             * id : 4d4c7214e64a4dc696de9d5edc168dfd
             * mmbId : testshop001
             * mobilephone : 123
             * phone : 12312
             */

            private String accountname;
            private String accountno;
            private String bankname;
            private String contact;
            private String id;
            private String mmbId;
            private String mobilephone;
            private String phone;

            public String getAccountname() {
                return accountname;
            }

            public void setAccountname(String accountname) {
                this.accountname = accountname;
            }

            public String getAccountno() {
                return accountno;
            }

            public void setAccountno(String accountno) {
                this.accountno = accountno;
            }

            public String getBankname() {
                return bankname;
            }

            public void setBankname(String bankname) {
                this.bankname = bankname;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMmbId() {
                return mmbId;
            }

            public void setMmbId(String mmbId) {
                this.mmbId = mmbId;
            }

            public String getMobilephone() {
                return mobilephone;
            }

            public void setMobilephone(String mobilephone) {
                this.mobilephone = mobilephone;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}