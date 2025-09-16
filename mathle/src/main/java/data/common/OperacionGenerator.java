package data.common;

import java.util.Random;

public class OperacionGenerator {
    
    public static String generarOperacion(int longitud) {
        Random rand = new Random();
        String[] operadores = {"+", "-", "*", "/"};

        while (true) {
            StringBuilder expresion = new StringBuilder();
            int operando = 0;
            if (longitud == 6){
                operando = rand.nextInt(99) + 1 ; // 1 a 99
            }
            else{
                operando = rand.nextInt(100); // 0 a 99
            }
            int resultado = operando;
            expresion.append(operando);
            int longitudActual = String.valueOf(operando).length();

            while (true) {
                String operador = operadores[rand.nextInt(operadores.length)];
                int siguienteOperando = rand.nextInt(99) + 1; // evitamos 0 para divisiones

                int nuevoResultado;

                switch (operador) {
                    case "+":
                        nuevoResultado = resultado + siguienteOperando;
                        break;
                    case "-":
                        nuevoResultado = resultado - siguienteOperando;
                        if (nuevoResultado < 0) continue; // evitar negativos
                        break;
                    case "*":
                        nuevoResultado = resultado * siguienteOperando;
                        break;
                    case "/":
                        if (siguienteOperando == 0 || resultado % siguienteOperando != 0) continue;
                        nuevoResultado = resultado / siguienteOperando;
                        break;
                    default:
                        continue;
                }

                String parte = operador + siguienteOperando;
                int nuevaLongitud = longitudActual + parte.length() + 1 + String.valueOf(nuevoResultado).length(); // +1 for '='

                if (nuevaLongitud > longitud) break;

                expresion.append(parte);
                resultado = nuevoResultado;
                longitudActual += parte.length();
            }

            String total = expresion + "=" + resultado;
            if (total.length() == longitud) {
                return total;
            }
        }
    }
}
