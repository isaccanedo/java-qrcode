package br.com.isaccanedo.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.input.DataFormat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static br.com.isaccanedo.qrcode.Encrypt.encryptThisString;

/**
 * @author Isac Canedo
 */
public class QRCodeGenerator {

    static Date date = new Date();
    static SimpleDateFormat f = new SimpleDateFormat("ddMMyyyyhhmmss");
    static String data1 = f.format(date);
    Encrypt encrypt = new Encrypt();
    static String isac = encryptThisString(data1);
    private static final String QR_CODE_IMAGE_PATH = "./" + isac + ".png";


    private static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    private byte[] getQRCodeImageByteArray(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    public static void main(String[] args) {
        try {
            generateQRCodeImage("Isac Canedo de Almeida", 350, 350, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Não foi possível gerar o código QR, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Não foi possível gerar o código QR, IOException :: " + e.getMessage());
        }
    }
}
