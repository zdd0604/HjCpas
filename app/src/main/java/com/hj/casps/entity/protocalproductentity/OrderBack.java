package com.hj.casps.entity.protocalproductentity;

public class OrderBack {

    /**
     * data : true
     * return_code : 0
     * return_message : success
     */

    private boolean data;
    private int return_code;
    private String return_message;

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
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
}