package code;

import java.io.File;
import java.util.Scanner;

public class EncryptFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Getting the password
        System.out.println("Enter 16 digit password only to encrypt the file:");
        String password = scanner.nextLine();
        System.out.println();

        // Getting the file's path
        System.out.println("Enter the full path of the file to encrypt the file:");
        String pathOfFile = scanner.nextLine();
        System.out.println();

        // Encryption
        File filePathName = new File(pathOfFile);
        Encryption encryptFile = new Encryption();
        boolean encryptionSuccess = encryptFile.encrypt(filePathName, password);
        if (encryptionSuccess) System.out.println("File is successfully encrypted.");
        scanner.close();
    }
}
