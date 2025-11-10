import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un nodo dentro del Árbol B+.
 * Cada nodo puede ser hoja o interno.
 * Si es hoja, contiene los datos y un puntero a la siguiente hoja.
 * Si es interno, contiene claves y referencias a sus hijos.
 */
public class Nodo {
    private final List<Integer> listaClaves;
    private final List<Nodo> listaHijos;
    private boolean esHoja;
    private Nodo siguienteHoja;

    //Constructor del nodo.
    //@param esHoja indica si el nodo es hoja.

    public Nodo(boolean esHoja) {
        this.esHoja = esHoja;
        this.listaClaves = new ArrayList<>();
        this.listaHijos = new ArrayList<>();
        this.siguienteHoja = null;
    }

    //Inserta una clave de manera ordenada dentro del nodo.

    public void insertarClave(int clave) {
        int i = 0;
        while (i < listaClaves.size() && listaClaves.get(i) < clave) {
            i++;
        }
        listaClaves.add(i, clave);
    }

    //Divide el nodo cuando alcanza el máximo permitido de claves.
    //@return nuevo nodo creado tras la división.

    public Nodo dividir() {
        int medio = listaClaves.size() / 2;
        Nodo nuevoNodo = new Nodo(this.esHoja);

        for (int i = medio; i < listaClaves.size(); i++) {
            nuevoNodo.listaClaves.add(this.listaClaves.get(i));
        }

        if (!this.esHoja) {
            for (int i = medio + 1; i < listaHijos.size(); i++) {
                nuevoNodo.listaHijos.add(this.listaHijos.get(i));
            }
        }

        if (this.esHoja) {
            nuevoNodo.siguienteHoja = this.siguienteHoja;
            this.siguienteHoja = nuevoNodo;
        }

        while (this.listaClaves.size() > medio) {
            this.listaClaves.remove(this.listaClaves.size() - 1);
        }

        while (!this.esHoja && this.listaHijos.size() > medio + 1) {
            this.listaHijos.remove(this.listaHijos.size() - 1);
        }

        return nuevoNodo;
    }

    // Getters y setters mínimos

    public List<Integer> getListaClaves() {
        return listaClaves;
    }

    public List<Nodo> getListaHijos() {
        return listaHijos;
    }

    public boolean isHoja() {
        return esHoja;
    }

    public void setHoja(boolean esHoja) {
        this.esHoja = esHoja;
    }

    public Nodo getSiguienteHoja() {
        return siguienteHoja;
    }

    public void setSiguienteHoja(Nodo siguienteHoja) {
        this.siguienteHoja = siguienteHoja;
    }
}
