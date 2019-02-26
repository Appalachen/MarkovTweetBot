package Gui;

import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class KeystoreLoader {
    private static KeyStore ks;
    private static char[] pwdArray = "asdfgh".toCharArray();

    public static void setJKS() {
        try {
            ks = KeyStore.getInstance(KeyStore.getDefaultType());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        try {
            if (ks != null) {
                ks.load(null, pwdArray);
            }
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream("newKeyStoreFileName.jks")) {
            if (ks != null) {
                ks.store(fos, pwdArray);
            }
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance("JKS");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            assert ks != null;
            ks.load(new FileInputStream("newKeyStoreFileName.jks"), pwdArray);
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
    }

    public static void storeKeys() {
        setJKS();

        SecretKey secretKey = new SecretKey() {
            @Override
            public String getAlgorithm() {
                return null;
            }

            @Override
            public String getFormat() {
                return null;
            }

            @Override
            public byte[] getEncoded() {
                return new byte[0];
            }
        };

        KeyStore.SecretKeyEntry secret
                = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter password
                = new KeyStore.PasswordProtection(pwdArray);
        try {
            ks.setEntry("db-encryption-secret", secret, password);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

    }
}
