package com.edisa.formacion.mayo2025;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
    @Produces("image/qr")
    public Response generarQR(
            @QueryParam("texto") String texto) {

        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(texto, BarcodeFormat.QR_CODE, 600, 400);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", baos);

            return Response.ok(new ByteArrayInputStream(baos.toByteArray())).type("image/qr").build();

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
    @Path("/persona")
    public Response crearPersona(Persona persona) {

        persona.setId(34);

        return Response.ok(persona).build();
    }

}
