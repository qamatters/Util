package ss;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Encrypt {
    private static String CIPHER_ALGORITHM_AES = "AES/ECB/PKCS5Padding";
    private static String UTF_8 = "UTF-8";
    private static String AES_ALGORITHM = "AES";
    private static String CIPHER_ALGORITHM_SHA = "SHA-1";
    private static SecretKeySpec secretKeySpec;
    private static byte[] key;
    private static MessageDigest sha;

    public static void setKey(String mykey) {
        {
            try {
                key = mykey.getBytes(UTF_8);
                sha = MessageDigest.getInstance(CIPHER_ALGORITHM_SHA);
                key = sha.digest(key);
                key = Arrays.copyOf(key, 16);
                secretKeySpec = new SecretKeySpec(key, AES_ALGORITHM);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public static String encryptString(String StringToEncrypt, String myKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        setKey(myKey);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return Base64.encodeBase64String(cipher.doFinal(StringToEncrypt.getBytes(UTF_8)));
    }

    public static String decryptedString(String encodedText, String myKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        setKey(myKey);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return new String(cipher.doFinal(Base64.decodeBase64(encodedText)));

    }
}
