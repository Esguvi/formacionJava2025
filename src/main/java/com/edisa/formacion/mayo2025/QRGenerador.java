package com.edisa.formacion.mayo2025;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.*;

public class QRGenerador {


    public static void main(String[] args) {
        String texto = "";
        String rutaQR = "";
        String formatoImagen = "png";

        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("Debes de introducir 2 parámetros: [texto] [ruta]");
            }
            else {
                texto = args[0];
                rutaQR = args[1];
            }
            BitMatrix bitMatrix = new QRCodeWriter().encode(texto, BarcodeFormat.QR_CODE, 600, 400);
            FileOutputStream outputStream = new FileOutputStream(new File(rutaQR));
            MatrixToImageWriter.writeToStream(bitMatrix, formatoImagen, outputStream);
            System.out.println("QR generado con éxito en: " + rutaQR);
        } catch (IllegalArgumentException iae) {
            System.err.println("Error: " + iae.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}