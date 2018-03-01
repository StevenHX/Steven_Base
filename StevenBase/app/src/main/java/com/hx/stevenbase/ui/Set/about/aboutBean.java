package com.hx.stevenbase.ui.Set.about;

/**
 * Created by user on 2018/1/16.
 */

public class aboutBean  {
        private double avaBalance;
        private int isCredit;
        private String mobile;
        private String name;
        private boolean payPswExist;
        private String token;

        public double getAvaBalance() {
            return avaBalance;
        }

        public void setAvaBalance(double avaBalance) {
            this.avaBalance = avaBalance;
        }

        public int isCredit() {
            return isCredit;
        }

        public void setCredit(int credit) {
            isCredit = credit;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPayPswExist() {
            return payPswExist;
        }

        public void setPayPswExist(boolean payPswExist) {
            this.payPswExist = payPswExist;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
}
