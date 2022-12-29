package com.example.projectpmob;

import android.os.Bundle;

public class pencarian {
    private String imageRM, namaRM, ketRM, notelp,alamatRM;
    private Double latTujuan, lonTujuan;

    public String getImageRM() {
        return imageRM;
    }

    public String getNamaRM() {
        return namaRM;
    }

    public String getKetRM() {
        return ketRM;
    }

    public String getNoTelp() {
        return notelp;
    }

    public String getAlamatRM() {
        return alamatRM;
    }

    public Double getlatTujuan(){return latTujuan;}

    public Double getlonTujuan(){return lonTujuan;}

    public void setAmbilLagi(String a){
        if (a == "RM Pondok Ndeso"){
            setDataRm(1);
        }

        if (a == "Rumah Makan Masakan Padang Tamanan"){
            setDataRm(2);
        }

        if (a == "Rumah Makan Yogi Rohim Hidayat"){
            setDataRm(3);
        }

        if (a == "Rumah Makan dan Sate Padang Batusangka"){
            setDataRm(4);
        }

        if (a == "Rumah Makan Padang Salero Kito"){
            setDataRm(5);
        }

        if (a == "Ayam Geprek 77 Mas Agus"){
            setDataRm(6);
        }

        if (a == "Bale Ayu Giwangan"){
            setDataRm(7);
        }

        if (a == "Rumah Makan Sabar Bundo"){
            setDataRm(8);
        }

        if (a == "Rumah Makan & Seafood 99"){
            setDataRm(9);
        }
    }

    public void setDataRm(int a){
        if(a == 1){
            setDataRM("RM Pondok Ndeso");
        }

        if(a == 2){
            setDataRM("Rumah Makan Masakan Padang Tamanan");
        }

        if(a == 3){
            setDataRM("Rumah Makan Yogi Rohim Hidayat");
        }

        if(a == 4){
            setDataRM("Rumah Makan dan Sate Padang Batusangka");
        }

        if(a == 5){
            setDataRM("Rumah Makan Padang Salero Kito");
        }

        if(a == 6){
            setDataRM("Ayam Geprek 77 Mas Agus");
        }

        if(a == 7){
            setDataRM("Bale Ayu Giwangan");
        }

        if(a == 8){
            setDataRM("Rumah Makan Sabar Bundo");
        }

        if(a == 9){
            setDataRM("Rumah Makan & Seafood 99");
        }
    }

    public void setDataRM(String b) {
        if(b.equals("RM Pondok Ndeso")){
            imageRM = "rmpdkdeso";
            namaRM = "RM Pondok Ndeso";
            ketRM= "Opsi layanan:\n 1. Tempat duduk di area terbuka\n 2. Antar tanpa bertemu\n 3. Pesan antar\n 3. Bawa pulang\n 4. Makan di tempat ";
            notelp = "02744396627";
            alamatRM = "Jl. Imogiri Tim., Giwangan, Kec. Umbulharjo, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55163";
            latTujuan = -7.839943;
            lonTujuan = 110.3870012;
        }

        if(b.equals("Rumah Makan Masakan Padang Tamanan")){
            imageRM = "pdgtamanan";
            namaRM = "Rumah Makan Masakan Padang Tamanan";
            ketRM = "Opsi layanan:\n 1. Tempat duduk di area terbuka\n 2. Antar tanpa bertemu\n 3. Pesan antar\n 3. Bawa pulang\n 4. Makan di tempat ";
            notelp = "087738371008";
            alamatRM = "Jl. Ki Ageng Pemanahan No.24, Kragilan, Tamanan, Kec. Banguntapan, Kabupaten Bantul, Daerah Istimewa Yogyakarta 55191";
            latTujuan = -7.8335335;
            lonTujuan = 110.3645777;
        }

        if(b.equals("Rumah Makan Yogi Rohim Hidayat")){
            imageRM = "rmyrhidayat";
            namaRM = "Rumah Makan Yogi Rohim Hidayat";
            ketRM = "Opsi layanan:\n 1. Tempat duduk di area terbuka\n 2. Antar tanpa bertemu\n 3. Pesan antar\n 3. Bawa pulang\n 4. Makan di tempat ";
            notelp = "081327630808";
            alamatRM = "Jl. Kalimo Sodo No.42, Krobokan, Tamanan, Kec. Banguntapan, Kabupaten Bantul, Daerah Istimewa Yogyakarta 55191";
            latTujuan = -7.8412453;
            lonTujuan = 110.3825961;
        }

        if(b.equals("Rumah Makan dan Sate Padang Batusangka")){
            imageRM = "rmspbatusangka";
            namaRM = "Rumah Makan dan Sate Padang Batusangka";
            ketRM = "Opsi layanan:\n 1. Tempat duduk di area terbuka\n 2. Antar tanpa bertemu\n 3. Pesan antar\n 3. Bawa pulang\n 4. Makan di tempat ";
            notelp = "-";
            alamatRM = "59CQ+2R2, Giwangan, Kec. Umbulharjo, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55163";
            latTujuan = -7.8411971;
            lonTujuan = 110.3672751;
        }

        if(b.equals("Rumah Makan Padang Salero Kito")){
            imageRM = "rmpselkito";
            namaRM = "Rumah Makan Padang Salero Kito";
            ketRM = "Opsi layanan:\n 1. Tempat duduk di area terbuka\n 2. Antar tanpa bertemu\n 3. Pesan antar\n 3. Bawa pulang\n 4. Makan di tempat ";
            notelp = "083877456500";
            alamatRM = "597J+XV5, Tamanan, Kec. Banguntapan, Kabupaten Bantul, Daerah Istimewa Yogyakarta 55191";
            latTujuan = -7.8407909;
            lonTujuan = 110.2446954;
        }
        if(b.equals("Ayam Geprek 77 Mas Agus")){
            imageRM = "agagus";
            namaRM = "Ayam Geprek 77 Mas Agus";
            ketRM = "Opsi layanan:\n 1. Tempat duduk di area terbuka\n 2. Antar tanpa bertemu\n 3. Pesan antar\n 3. Bawa pulang\n 4. Makan di tempat ";
            notelp = "089672202632";
            alamatRM = "Tanjung, Bangunharjo, Kec. Sewon, Kabupaten Bantul, Daerah Istimewa Yogyakarta 55188";
            latTujuan = -7.8350696;
            lonTujuan = 110.3714008;
        }

        if(b.equals("Bale Ayu Giwangan")){
            imageRM = "bagiwangan";
            namaRM = "Bale Ayu Giwangan";
            ketRM = "Opsi layanan:\n 1. Tempat duduk di area terbuka\n 2. Antar tanpa bertemu\n 3. Pesan antar\n 3. Bawa pulang\n 4. Makan di tempat ";
            notelp = "02744396653";
            alamatRM = "Jl. Imogiri Tim. No.KM 6, Nglebeng, Tamanan, Kec. Banguntapan, Kabupaten Bantul, Daerah Istimewa Yogyakarta 55791";
            latTujuan = -7.8399681;
            lonTujuan = 110.3892789;
        }

        if(b.equals("Rumah Makan Sabar Bundo")){
            imageRM = "rmsbundo";
            namaRM = "Rumah Makan Sabar Bundo";
            ketRM = "Opsi layanan:\n 1. Tempat duduk di area terbuka\n 2. Antar tanpa bertemu\n 3. Pesan antar\n 3. Bawa pulang\n 4. Makan di tempat ";
            notelp = "-";
            alamatRM = "Jl. Jambon No.490B, Rogoyudan, Kricak, Kec. Tegalrejo, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55242";
            latTujuan = -7.7710993;
            lonTujuan = 110.2270554;
        }
        if(b.equals("Rumah Makan & Seafood 99")) {
            imageRM = "rmsf99";
            namaRM = "Rumah Makan & Seafood 99";
            ketRM = "Opsi layanan:\n 1. Tempat duduk di area terbuka\n 2. Antar tanpa bertemu\n 3. Pesan antar\n 3. Bawa pulang\n 4. Makan di tempat ";
            notelp = "-";
            alamatRM = "59W7+36C, Unnamed Road, Ngupasan, Kec. Gondomanan, Kota Yogyakarta, Daerah Istimewa Yogyakarta 55132";
            latTujuan = -7.7707476;
            lonTujuan = 110.2270551;
        }
    }
}
