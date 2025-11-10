/**
 * Clase que implementa la estructura de datos Árbol B+.
 * Contiene las operaciones principales: insertar, buscar, eliminar y recorrer.
 * Mantiene el árbol balanceado automáticamente.
 */
public class ArbolBMas {
    private Nodo raiz;
    private final int orden;

    //Constructor del Árbol B+.
    //@param orden número máximo de hijos por nodo (debe ser >= 3).

    public ArbolBMas(int orden) {
        if (orden < 3) {
            throw new IllegalArgumentException("El orden debe ser mayor o igual a 3.");
        }
        this.orden = orden;
        this.raiz = new Nodo(true);
    }

    public int getOrden() {
        return orden;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    // Inserta una nueva clave en el árbol. //
    public void insertar(int clave) {
        Nodo nodo = raiz;

        if (nodo.getListaClaves().size() == orden - 1) {
            Nodo nuevaRaiz = new Nodo(false);
            nuevaRaiz.getListaHijos().add(raiz);
            dividirHijo(nuevaRaiz, 0, raiz);
            raiz = nuevaRaiz;
        }

        insertarNoLleno(raiz, clave);
    }

    private void insertarNoLleno(Nodo nodo, int clave) {
        if (nodo.isHoja()) {
            nodo.insertarClave(clave);
        } else {
            int i = nodo.getListaClaves().size() - 1;
            while (i >= 0 && clave < nodo.getListaClaves().get(i)) {
                i--;
            }
            i++;
            Nodo hijo = nodo.getListaHijos().get(i);

            if (hijo.getListaClaves().size() == orden - 1) {
                dividirHijo(nodo, i, hijo);
                if (clave > nodo.getListaClaves().get(i)) {
                    i++;
                }
            }

            insertarNoLleno(nodo.getListaHijos().get(i), clave);
        }
    }

    private void dividirHijo(Nodo padre, int indice, Nodo hijo) {
        Nodo nuevo = hijo.dividir();
        int clavePromocionada = nuevo.getListaClaves().get(0);
        padre.getListaClaves().add(indice, clavePromocionada);
        padre.getListaHijos().add(indice + 1, nuevo);
    }

    // Busca una clave en el árbol.
    public boolean buscar(int clave) {
        return buscarRec(raiz, clave);
    }

    private boolean buscarRec(Nodo nodo, int clave) {
        int i = 0;
        while (i < nodo.getListaClaves().size() && clave > nodo.getListaClaves().get(i)) {
            i++;
        }
        if (i < nodo.getListaClaves().size() && clave == nodo.getListaClaves().get(i)) {
            return true;
        }
        if (nodo.isHoja()) {
            return false;
        } else {
            return buscarRec(nodo.getListaHijos().get(i), clave);
        }
    }

    // Elimina una clave
    public void eliminar(int clave) {
        eliminarRec(raiz, clave);
    }

    private void eliminarRec(Nodo nodo, int clave) {
        if (nodo.isHoja()) {
            nodo.getListaClaves().removeIf(k -> k == clave);
        } else {
            int i = 0;
            while (i < nodo.getListaClaves().size() && clave > nodo.getListaClaves().get(i)) {
                i++;
            }
            eliminarRec(nodo.getListaHijos().get(i), clave);
        }
    }

    // Recorre e imprime los primeros n elementos desde la primera hoja.
    public void recorrer(int n) {
        Nodo actual = raiz;
        while (!actual.isHoja()) {
            actual = actual.getListaHijos().get(0);
        }
        int cont = 0;
        while (actual != null && cont < n) {
            for (int clave : actual.getListaClaves()) {
                System.out.print(clave + " ");
                cont++;
                if (cont >= n) break;
            }
            actual = actual.getSiguienteHoja();
        }
        System.out.println();
    }
}


