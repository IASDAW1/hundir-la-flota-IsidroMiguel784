public class Barco {
    private int longitud;
    private String nombre;
    private boolean[] partes;

    public Barco(int longitud, String nombre) {
        this.longitud = longitud;
        this.nombre = nombre;
        this.partes = new boolean[longitud];
    }

    public int getLongitud() {
        return longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean haSidoHundido() {
        for (boolean parte : partes) {
            if (!parte) {
                return false;
            }
        }
        return true;
    }

    public void hundirParte(int parte) {
        partes[parte] = true;
    }
}