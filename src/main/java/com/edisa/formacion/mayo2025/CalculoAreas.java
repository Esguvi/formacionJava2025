package com.edisa.formacion.mayo2025;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class CalculoAreas {
    public static void main(String[] args) {
        String figura = args[0];
        double area = 0;

        try {

            if (args.length > 3) {
                throw new IllegalArgumentException("Debes introducir sólo la figura que quieres esocger (rectángulo o círculo) y la/s medida/s dependiendo de la figura escogida, para poder calcular el área de dicha figura.");
            }

            if (args[0].isEmpty() || !args[0].equals("rectángulo") && !args[0].equals("círculo")) {
                throw new IllegalArgumentException("Debes de introducir que figura quieres escoger (rectángulo o círculo).");
            }

            if (args[0].equals("círculo") && args.length > 2) {
                throw new IllegalArgumentException("No debes de introducir más de 2 argumentos para calcular el área del círculo.");
            }

            if (args[1].isEmpty()) {
                throw new IllegalArgumentException("La primera medida no puede estar vacía.");
            } else if (Double.parseDouble(args[1]) < 0) {
                throw new IllegalArgumentException("La primera medida no puede ser negativa.");
            }

            if (args[0].equals("rectángulo") && Double.parseDouble(args[2]) < 0) {
                throw new IllegalArgumentException("La segunda medida no puede ser negativa.");
            }

            switch (figura) {
                case "rectángulo":
                    area = Double.parseDouble(args[1]) * Double.parseDouble(args[2]);
                    System.out.println("El área de un rectángulo es: " + area);
                    break;

                case "círculo":
                    area = Math.PI * Double.parseDouble(args[1]);
                    System.out.println("El área del círculo es: " + area);
                    break;
            }

        } catch (NumberFormatException nfe) {
            if (!args[1].matches("[0-9]+(\\.[0-9]+)?")) {
                System.err.println("Error: El primer parámetro no puede contener letras.");
            }
            if (args.length > 2 && !args[2].matches("[0-9]+(\\.[0-9]+)?")) {
                System.err.println("Error: El segundo parámetro no puede contener letras.");
            }
        } catch (IllegalArgumentException iae) {
            System.err.println("Error: " + iae.getMessage());
        }
    }
}