
import java.util.Random;
import java.util.Scanner;

public class Tablero {
    private char[][] tablero;
    private Barco[] barcos;
    private int filas;
    private int columnas;

    public Tablero(int filas, int columnas, Barco[] barcos) {
        this.filas = filas;
        this.columnas = columnas;
        this.tablero = new char[filas][columnas];
        this.barcos = barcos;
    }

    public void inicializarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = '~'; // Carácter que representa el agua
            }
        }
    }

    public void imprimirTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void colocarBarcos() {
        Random rand = new Random();
        for (Barco barco : barcos) {
            int fila, columna;
            do {
                fila = rand.nextInt(filas);
                columna = rand.nextInt(columnas);
            } while (!puedeColocarBarco(barco, fila, columna));
            colocarBarcoEnTablero(barco, fila, columna);
        }
    }

    public boolean puedeColocarBarco(Barco barco, int fila, int columna) {
        if (fila + barco.getLongitud() > filas || columna + 1 > columnas) {
            return false;
        }
        for (int i = 0; i < barco.getLongitud(); i++) {
            if (tablero[fila + i][columna] != '~') {
                return false;
            }
        }
        return true;
    }

    public void colocarBarcoEnTablero(Barco barco, int fila, int columna) {
        for (int i = 0; i < barco.getLongitud(); i++) {
            tablero[fila + i][columna] = 'B'; // Carácter que representa un barco
        }
    }

    public void jugar() {
        Scanner scanner = new Scanner(System.in);
        while (!todosLosBarcosHundidos()) {
            System.out.println("Ingrese fila y columna para disparar (separadas por espacio):");
            int fila = scanner.nextInt();
            int columna = scanner.nextInt();
            if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
                System.out.println("Coordenadas fuera de rango. Inténtelo de nuevo.");
                continue;
            }
            if (tablero[fila][columna] == 'X' || tablero[fila][columna] == 'O') {
                System.out.println("Ya ha disparado en esta posición. Inténtelo de nuevo.");
                continue;
            }
            boolean golpe = false;
            for (Barco barco : barcos) {
                if (fila < barco.getLongitud() && tablero[fila][columna] == 'B') {
                    barco.hundirParte(fila);
                    tablero[fila][columna] = 'X'; // Carácter que representa un golpe
                    golpe = true;
                    System.out.println("¡Golpe!");
                    break;
                }
            }
            if (!golpe) {
                tablero[fila][columna] = 'O'; // Carácter que representa un fallo
                System.out.println("¡Fallo!");
            }
            imprimirTablero();
        }
        System.out.println("¡Todos los barcos han sido hundidos! ¡Felicidades!");
        scanner.close();
    }

    private boolean todosLosBarcosHundidos() {
        for (Barco barco : barcos) {
            if (!barco.haSidoHundido()) {
                return false;
            }
        }
        return true;
    }
}