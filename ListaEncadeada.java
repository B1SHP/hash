public class ListaEncadeada<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public int size() {
        return size;
    }

    public void add(T data) {

        Node<T> newNode = new Node<>(data);

        if (tail == null) {

            head = tail = newNode;

        } else {

            tail.next = newNode;

            newNode.prev = tail;

            tail = newNode;

        }

        size++;

    }

    public T find(String code) {

        Node<T> current = head;

        while (current != null) {

            Registro registro = (Registro) current.data;

            if (registro.getCodigo().equals(code)) {

                return current.data;

            }

            current = current.next;

        }

        return null;

    }

    private static class Node<T> {

        T data;
        Node<T> next;
        Node<T> prev;

        public Node(T data) {
            this.data = data;
        }

    }
}
