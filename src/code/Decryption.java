package code;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

public class Decryption {
    public boolean decrypt(File fileName, String key)
    {
        boolean successfullyDecryptedFlag = false;

        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        File newFileName = null;

        try
        {
            // Decryption
            newFileName = new File(fileName.getPath() + ".Decrypted_AES_ECB");
            fileOutputStream = new FileOutputStream(newFileName);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            try
            {
                // File Handling
                FileInputStream fileInputStream = null;
                BufferedInputStream bufferedInputStream = null;
                try
                {
                    fileInputStream = new FileInputStream(fileName);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);

                    // Much Crucial
                    byte [] byteArray = new byte[8208];
                    int readSize = 0;

                    while((readSize = bufferedInputStream.read(byteArray)) != -1)
                    {
                        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                        // Use SHA 512 first 16 bytes for 128 bit key size
                        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
                        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                        byte [] byteToWrite = cipher.doFinal(byteArray, 0, readSize);
                        bufferedOutputStream.write(byteToWrite, 0, byteToWrite.length);
                        bufferedOutputStream.flush();
                    }
                    successfullyDecryptedFlag = true;
                }
                catch(Exception e) { e.printStackTrace(); }
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
            catch (Exception e) { e.printStackTrace(); }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            try
            {
                if (bufferedOutputStream != null) bufferedOutputStream.close();
                if (fileOutputStream != null) fileOutputStream.close();
            }
            catch (Exception e) { e.printStackTrace(); }
        }

        if(successfullyDecryptedFlag)
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
