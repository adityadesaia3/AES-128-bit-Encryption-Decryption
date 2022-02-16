package code;

import java.io.File;
import java.util.Scanner;

public class DecryptFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Getting the password
        System.out.println("Enter password to decrypt the file:");
        String password = scanner.nextLine();
        System.out.println();

        // Getting the file's path
        System.out.println("Enter the full path of the file to decrypt the file:");
        String pathOfFile = scanner.nextLine();
        System.out.println();

        // Decryption
        File filePathName = new File(pathOfFile);
        Decryption decryptFile = new Decryption();
        boolean decryptionSuccess = decryptFile.decrypt(filePathName, password);
        if (decryptionSuccess) System.out.println("File is successfully decrypted.");

        scanner.close();
    }
}
