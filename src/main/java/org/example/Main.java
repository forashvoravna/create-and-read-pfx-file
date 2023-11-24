package org.example;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;


import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.jce.spec.GOST3410ParameterSpec;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.test.TestRandomBigInteger;
import org.bouncycastle.x509.X509V1CertificateGenerator;
import org.paynet.math.ec.ECConstants;
import org.paynet.math.ec.ECCurve;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Enumeration;

// PFX faylni o'qish
public class Main {

    private Long number = 1L;

    public Main() throws NoSuchAlgorithmException, NoSuchProviderException {
    }

    public Long getNumber() {
        return this.number;
    }

    public String getFileName() {
        if (this.number.toString().length() == 1) {
            return "DS00000" + number;
        } else if (this.number.toString().length() == 2) {
            return "DS0000" + number;
        } else if (this.number.toString().length() == 3) {
            return "DS000" + number;
        } else if (this.number.toString().length() == 4) {
            return "DS00" + number;
        } else if (this.number.toString().length() == 5) {
            return "DS0" + number;
        } else if (this.number.toString().length() == 6) {
            return "DS" + number;
        } else return null;
    }

    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        GOST3410ParameterSpec gost3410P = new GOST3410ParameterSpec(CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_B.getId());

        KeyPairGenerator g = KeyPairGenerator.getInstance("GOST3410", "BC");
        g.initialize(gost3410P, new SecureRandom());
        KeyPair p = g.generateKeyPair();

        PrivateKey sKey = p.getPrivate();
        PublicKey vKey = p.getPublic();

        Main main = new Main();
//        main.certificate();
//        try {
//            main.fileRead();
//        } catch (UnrecoverableKeyException ex) {
//            throw new RuntimeException(ex);
//        } catch (KeyStoreException ex) {
//            throw new RuntimeException(ex);
//        } catch (NoSuchAlgorithmException ex) {
//            throw new RuntimeException(ex);
//        }
//        main.fileEdit();
//        main.setNumber();
        main.generateCertificate();
        main.keyStoreTest(sKey, vKey);
    }


    private void fileEdit() {
        KeyStore keyStore;
        {
            try {
                keyStore = KeyStore.getInstance("PKCS12");
            } catch (KeyStoreException e) {
                throw new RuntimeException(e);
            }
        }

        char[] password = "24061996".toCharArray();

        try (
                FileInputStream fis = new FileInputStream("C:\\Users\\user\\Desktop\\PFX_file/" + getFileName() + ".pfx")) {
            keyStore.load(fis, password);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    private void fileRead() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        // PFX faylni o'qish
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        char[] password = "24061996".toCharArray();

        try (FileInputStream fis = new FileInputStream("C:\\DSKEYS\\DS4997124990002_Fazolat_Mukimova_24061996.pfx")) {
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
    }

    public void setNumber() {
        this.number += 1;
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
//--------------------------------------------------------------------//
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
                fos = new FileOutputStream("C:\\Users\\user\\Desktop\\PFX_file/" + getFileName() + ".pfx");
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



    public void generateCertificate() throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        GOST3410ParameterSpec gost3410P = new GOST3410ParameterSpec(CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_B.getId());

        KeyPairGenerator g = KeyPairGenerator.getInstance("GOST3410", "BC");
        g.initialize(gost3410P, new SecureRandom());
        KeyPair p = g.generateKeyPair();

        PrivateKey sKey = p.getPrivate();
        PublicKey vKey = p.getPublic();

        KeyStore ks = KeyStore.getInstance("PKCS12");

        ks.load(null, null);

        X509Certificate cert = TestCertificateGen.createSelfSignedCert("CN=Test", "GOST3411withGOST3410", new KeyPair(vKey, sKey));

        ks.setKeyEntry("Anvarov Sharofiddin Sobitali o'g'li 19970.08.08 Farg'ona viloyati quva tumani", sKey, "gost".toCharArray(), new Certificate[]{cert});

        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        ks.store(bOut, "gost".toCharArray());

        ks = KeyStore.getInstance("PKCS12");

        ks.load(new ByteArrayInputStream(bOut.toByteArray()), "gost".toCharArray());

        Enumeration<String> aliases = null;
        try {
            aliases = ks.aliases();

        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }

        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            System.out.println("Alias: " + alias);
//--------------------------------------------------------------------//
            try {
                cert = (X509Certificate) ks.getCertificate(alias);
            } catch (KeyStoreException e) {
                throw new RuntimeException(e);
            }
// save your cert inside the keystore
            try {
                ks.setCertificateEntry("YourCertAlias", cert);
            } catch (KeyStoreException e) {
                throw new RuntimeException(e);
            }
// create the outputstream to store the keystore
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("C:\\Users\\user\\Desktop\\PFX_file/" + getFileName() + ".pfx");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
// store the keystore protected with password
            try {
                ks.store(fos, "gost".toCharArray());
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

    private void keyStoreTest(PrivateKey sKey, PublicKey vKey)
            throws Exception
    {
        //
        // keystore test
        //
        KeyStore ks = KeyStore.getInstance("PKCS12");

        ks.load(null, null);

        X509Certificate cert = TestCertificateGen.createSelfSignedCert("CN=Test", "GOST3411withGOST3410", new KeyPair(vKey, sKey));

        ks.setKeyEntry("gost", sKey, "gost".toCharArray(), new Certificate[]{cert});

        ByteArrayOutputStream bOut = new ByteArrayOutputStream();

        ks.store(bOut, "gost".toCharArray());

        ks = KeyStore.getInstance("PKCS12");

        ks.load(new ByteArrayInputStream(bOut.toByteArray()), "gost".toCharArray());
    }

}