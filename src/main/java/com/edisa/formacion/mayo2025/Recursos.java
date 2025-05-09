package com.edisa.formacion.mayo2025;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class Recursos {

    @GET
    @Path("/saludo")
    public Response saludar(@QueryParam("nombre") String nombre,
                          @QueryParam("apellido") String apellido,
                          @QueryParam("edad") int edad) {

        return Response.status(404).build();
    }

    @POST
    @Path("/saludo/post")
    public String saludar_post(@QueryParam("nombre") String nombre,
                          @QueryParam("apellido") String apellido,
                          @QueryParam("edad") int edad) {

        return "Hola desde el metodo POST, " + nombre + " " + apellido + ". Tienes " + edad + " años.";
    }

    @GET
    @Path("/codabar/generarqr")
    @Produces("image/png")
    public Response generarQR(
            @QueryParam("texto") String texto) {

        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(texto, BarcodeFormat.QR_CODE, 600, 400);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", baos);

            return Response.ok(new ByteArrayInputStream(baos.toByteArray())).type("image/png").build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al generar el código QR: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/codabar/generar_codigo_barras")
    @Produces("image/png")
    public Response generarCodigoBarras(
            @QueryParam("texto") String texto,
            @QueryParam("formato_codigo_barras") String formatoCodigoBarras) {

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(texto, BarcodeFormat.valueOf(formatoCodigoBarras), 600, 400);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);

            return Response.ok(new ByteArrayInputStream(baos.toByteArray())).type("image/png").build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al generar el código de barras: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/codabar/leer_codigo_barras")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public Response leerCodigoBarras(InputStream imagenCargada) {
        try {
            BufferedImage bf = ImageIO.read(imagenCargada);
            LuminanceSource ls = new BufferedImageLuminanceSource(bf);
            BinaryBitmap bbitmap = new BinaryBitmap(new HybridBinarizer(ls));

            MultiFormatReader mfr = new MultiFormatReader();

            GenericMultipleBarcodeReader reader = new GenericMultipleBarcodeReader(mfr);
            Result[] results = reader.decodeMultiple(bbitmap);

            List<Map<String, String>> resultados = new ArrayList<>();
            for (Result result : results) {
                Map<String, String> resultado = new HashMap<>();
                resultado.put("texto", result.getText());
                resultados.add(resultado);
            }

            return Response.ok(resultados).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al procesar la imagen: " + e.getMessage()).build();
        }
    }


    @POST
    @Path("/persona")
    public Response crearPersona(Persona persona) {

        persona.setId(34);

        return Response.ok(persona).build();
    }

}
