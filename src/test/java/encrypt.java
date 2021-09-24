import ss.EmailUtility;
import ss.Encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.Message;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class encrypt {
    public static void main(String[] args) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String encrypt = Encrypt.encryptString("deepak", "username");
        String decryptedResult=  Encrypt.decryptedString("yrpc6A0v3V7bz1kOnfybFw==", "username");
        System.out.println(decryptedResult);
    }
}

