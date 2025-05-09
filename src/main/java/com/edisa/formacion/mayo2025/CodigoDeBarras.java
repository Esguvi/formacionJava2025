package com.edisa.formacion.mayo2025;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.*;

public class CodigoDeBarras {


    public static void main(String[] args) {
        String texto;
        String rutaQR;
        String formatoQR;
        String formatoImagen = "jpg";

        try {
            if (args.length != 3) {
                throw new IllegalArgumentException("Debes de introducir 3 parámetros: [texto] [ruta] [formato_codigo]");
            }
            else {
                texto = args[0];
                rutaQR = args[1];
                formatoQR = args[2].toUpperCase();
            }

            BarcodeFormat barcodeFormat = BarcodeFormat.valueOf(formatoQR);

            if (barcodeFormat == BarcodeFormat.EAN_13) {
                if (!texto.matches("\\d{12}")) {
                    throw new IllegalArgumentException("EAN-13 debe tener exactamente 12 dígitos numéricos.");
                }
            }

            BitMatrix bitMatrix = new MultiFormatWriter().encode(texto, barcodeFormat, 750, 1000);
            FileOutputStream outputStream = new FileOutputStream(new File(rutaQR));
            MatrixToImageWriter.writeToStream(bitMatrix, formatoImagen, outputStream);
            System.out.println("Código de barras generado con éxito en: " + rutaQR);
        } catch (IllegalArgumentException iae) {
            System.err.println("Error: " + iae.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}