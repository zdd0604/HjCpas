package com.hj.casps.entity.appordercheckorder;

import java.io.Serializable;

public  class SellersAccountListBean implements Serializable{
    private static final long serialVersionUID = -2234173381597078249L;
    /**
         * accountname : 123123
         * accountno : 12312
         * bankname : 123123 12312
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