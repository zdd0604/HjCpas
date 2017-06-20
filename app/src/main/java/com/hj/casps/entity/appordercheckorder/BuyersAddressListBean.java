package com.hj.casps.entity.appordercheckorder;

import java.io.Serializable;

public class BuyersAddressListBean implements Serializable {
    private static final long serialVersionUID = 7248851605291544760L;
    /**
         * address : app test 2
         * id : 7fda1ceb40874b9aa7f8008487ac354e
         * mmbId : testschool001
         * contact :
         * mobilephone :
         * phone :
         * zipcode :
         */

        private String address;
        private String id;
        private String mmbId;
        private String contact;
        private String mobilephone;
        private String phone;
        private String zipcode;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
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
    }
