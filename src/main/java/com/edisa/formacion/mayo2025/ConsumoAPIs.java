package com.edisa.formacion.mayo2025;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ConsumoAPIs {
    public static void main(String[] args) {

        String divisaOrigen = args[0];
        double tasaOrigen = 0;
        String divisaDestino = args[1];
        double tasaDestino = 0;
        double cantidadCambiar = 1;
        double cantidadTotal = 0;
        URL url;


        try {
            if (args.length > 3) {
                throw new IllegalArgumentException("No se pueden superar los 3 argumentos");
            }
            if (args.length == 3 && !args[2].isEmpty()) {
                cantidadCambiar = Double.parseDouble(args[2]);
            }
            url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            InputStream inputStream = con.getInputStream();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList cubeList = doc.getElementsByTagName("Cube");

            if (divisaOrigen.equals(("EUR"))) {
                tasaOrigen = 1;
            }
            if (divisaDestino.equals("EUR")) {
                tasaDestino = 1;
            }

            for (int i = 0; i < cubeList.getLength(); i++) {
                Element cube = (Element) cubeList.item(i);
                if (cube.hasAttribute("currency")) {
                    String currency = cube.getAttribute("currency");
                    double rate = Double.parseDouble(cube.getAttribute("rate"));

                    if (currency.equalsIgnoreCase(divisaOrigen)) {
                        tasaOrigen = rate;
                    }
                    if (currency.equalsIgnoreCase(divisaDestino)) {
                        tasaDestino = rate;
                    }
                }
            }

            cantidadTotal = (cantidadCambiar / tasaOrigen) * tasaDestino;

            System.out.println("La cantidad total es: " + cantidadTotal);

        } catch (IllegalArgumentException iae) {
            System.err.println("Error: " + iae.getMessage());
        } catch (MalformedURLException murle) {
            System.err.println("Error: La URL no es correcta.");
        } catch (IOException ioe) {
            System.err.println("Error de conexión: " + ioe.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }

    }
}