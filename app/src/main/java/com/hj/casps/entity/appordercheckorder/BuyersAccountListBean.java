package com.hj.casps.entity.appordercheckorder;

import java.io.Serializable;

public class BuyersAccountListBean implements Serializable {
    private static final long serialVersionUID = -3297785174711950994L;
    /**
         * accountname : 北京市朝阳区奥森学校
         * accountno : 9953001798001
         * bankname : 建设银行 9953001798001
         * contact : 丁丁
         * id : 0e4161c278a540328a7def34bf6f7696
         * mmbId : testschool001
         * mobilephone : 13501360137
         * phone :
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
