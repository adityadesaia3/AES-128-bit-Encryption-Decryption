package code;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

public class Encryption {
    boolean encrypt(File fileName, String key)
    {
        boolean successfullyEncryptedFlag = false;

        File newFileName = null;
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try
        {
            // Get parent folder and append the fileName + .Encrypted_AES_ECB to it
            newFileName = new File(fileName.getPath() + ".encrypted");
            fileOutputStream = new FileOutputStream(newFileName);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            // File Handling of reading the file for encryption
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            try
            {
                fileInputStream = new FileInputStream(fileName);
                bufferedInputStream = new BufferedInputStream(fileInputStream);

                byte [] byteArray = new byte[8192];
                int readSize = 0;

                while((readSize = bufferedInputStream.read(byteArray)) != -1)
                {
                    // Exception Handling
                    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

                    // Use SHA 512 first 16 bytes for 128 bit key size
                    SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
                    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

                    byte [] byteToWrite = cipher.doFinal(byteArray, 0, readSize);
                    bufferedOutputStream.write(byteToWrite);
                    bufferedOutputStream.flush();
                }
                successfullyEncryptedFlag = true;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (bufferedInputStream != null) bufferedInputStream.close();
                    if (fileInputStream != null) fileInputStream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (bufferedOutputStream != null) bufferedOutputStream.close();
                if (fileOutputStream != null) fileOutputStream.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if(successfullyEncryptedFlag)
        {
            String temp = fileName.getPath();
            fileName.delete();
            newFileName.renameTo(new File(temp));
            return true;
        }
        else
        {
            newFileName.delete();
            return false;
        }
    }
}
