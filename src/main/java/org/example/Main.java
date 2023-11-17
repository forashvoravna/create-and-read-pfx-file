package org.example;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;

// PFX faylni o'qish
public class Main {

    private Long number = 1L;

    public  Long getNumber(){
        return this.number;
    }
    public  String getFileName(){
        if (this.number.toString().length() == 1){
            return "DS00000"+number;
        }else if (this.number.toString().length() == 2){
            return "DS0000"+number;
        }else if (this.number.toString().length() == 3){
            return "DS000"+number;
        }else if (this.number.toString().length() == 4){
            return "DS00"+number;
        }else if (this.number.toString().length() == 5){
            return "DS0"+number;
        }else if (this.number.toString().length() == 6){
            return "DS"+number;
        }else return null;
    }

    public static void main(String[] args) {
        Main main = new Main();
        for (int i = 0; i < 10; i++) {
            main.main1();

        }
    }
    public void main1() {
        File myObj = new File("C:\\Users\\user\\Desktop\\PFX_file/"+getFileName()+".pfx");
        System.out.println(myObj.getAbsolutePath());
        System.out.println(new Date(1661880156561L));

        certificate();
        try {
            fileRead();
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        fileEdit();
        setNumber();
    }

    private  void fileEdit() {
        KeyStore keyStore;
        {
            try {
                keyStore = KeyStore.getInstance("PKCS12");
            } catch (KeyStoreException e) {
                throw new RuntimeException(e);
            }
        }

        char[] password = "********".toCharArray();

        try (
                FileInputStream fis = new FileInputStream("C:\\Users\\user\\Desktop\\PFX_file/"+getFileName()+".pfx")) {
            keyStore.load(fis, password);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    private static void fileRead() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        // PFX faylni o'qish
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        char[] password = "********".toCharArray();

        try (FileInputStream fis = new FileInputStream("C:\\DSKEYS\\DS4997124990002.pfx")) {
            keyStore.load(fis, password);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

// Ma'lumotlarni o'qib chiqish va taxrirlash
//        PrivateKey privateKey = (PrivateKey) keyStore.getKey("alias", password);
// Ma'lumotlarni o'zgartirishingiz mumkin

//// Yangi PFX faylni saqlash
//        try (FileOutputStream fos = new FileOutputStream("C:\\DSKEYS\\DSKEYS\\DS5879865580002.pfx")) {
//            keyStore.store(fos, password);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (CertificateException e) {
//            throw new RuntimeException(e);
//        } catch (KeyStoreException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
    }
    public  void setNumber(){
            this.number +=1;
    }

    private void certificate() {
//        Security.addProvider(new BouncyCastleProvider());

        String pfxFile = "C:\\DSKEYS\\DS4997124990002_Fazolat_Mukimova_24061996.pfx";
        char[] password = "24061996".toCharArray();

        // PFX faylni o'qish
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        try (FileInputStream fis = new FileInputStream(pfxFile)) {
            keyStore.load(fis, password);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // PFX fayl ichidagi kalit aliaslarini olish
        Enumeration<String> aliases = null;
        try {
            aliases = keyStore.aliases();

        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            System.out.println("Alias: " + alias);

//            // Shaxsiy kalit va sertifikatni olish
//            PrivateKey privateKey = null;
//            try {
//                privateKey = (PrivateKey) keyStore.getKey(alias, password);
//            } catch (KeyStoreException e) {
//                throw new RuntimeException(e);
//            } catch (NoSuchAlgorithmException e) {
//                throw new RuntimeException(e);
//            } catch (UnrecoverableKeyException e) {
//                throw new RuntimeException(e);
//            }
//            X509Certificate certificate = null;
//            try {
//                certificate = (X509Certificate) keyStore.getCertificate(alias);
//            } catch (KeyStoreException e) {
//                throw new RuntimeException(e);
//            }
//
//            // Kalit va sertifikat haqida boshqa ma'lumotlarni olish
//            System.out.println("Private Key: " + privateKey.toString());
//            System.out.println("Certificate: " + certificate.toString());

            //-----------------------------
            X509Certificate cert = null;
            try {
                cert = (X509Certificate) keyStore.getCertificate(alias);
            } catch (KeyStoreException e) {
                throw new RuntimeException(e);
            }
// save your cert inside the keystore
            try {
                keyStore.setCertificateEntry("YourCertAlias", cert);
            } catch (KeyStoreException e) {
                throw new RuntimeException(e);
            }
// create the outputstream to store the keystore
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("C:\\Users\\user\\Desktop\\PFX_file/"+getFileName()+".pfx");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
// store the keystore protected with password
            try {
                keyStore.store(fos, password);
            } catch (KeyStoreException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (CertificateException e) {
                throw new RuntimeException(e);
            }
        }
    }

}