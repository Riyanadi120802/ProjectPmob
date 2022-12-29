package com.example.projectpmob;

public class rmList {
        private String mNamaRM;
        private Double mJarakRM;

        public rmList(String vNama, Double vJarak) {
            mNamaRM = vNama;
            mJarakRM= vJarak;
        }
        public String getnamaRM() {
            return mNamaRM;
        }
        public Double getJarakRM() {
            return mJarakRM;
        }
    }
