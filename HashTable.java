import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HashTable {

    private ListaEncadeada<Registro>[] table;
    private int size;
    private int collisions = 0;

    public HashTable(int size) {
        this.size = size;
        table = new ListaEncadeada[size];
        
        for (int i = 0; i < size; i++) {
            table[i] = new ListaEncadeada<>();  
        }
    }

    private int hashDivision(String code) {
        return Integer.parseInt(code) % size;
    }

    private int hashMultiply(String code) {
        return (int) (size * ((Integer.parseInt(code) * 0.61887) % 1));
    }

    private int hashFold(String code) {
        int hash = 0;
        for (int i = 0; i < code.length(); i += 3) {
            int part = Integer.parseInt(code.substring(i, Math.min(i + 3, code.length())));
            hash += part;
        }
        return hash % size;
    }

    public void insert(Registro registro, int hashType) {
        int index;

        if (hashType == 1) {
            index = hashDivision(registro.getCodigo());
        } else if (hashType == 2) {
            index = hashMultiply(registro.getCodigo());
        } else {
            index = hashFold(registro.getCodigo());
        }

        ListaEncadeada<Registro> list = table[index];
        if (list.size() > 0) {
            collisions++;  
        }

        list.add(registro);  
    }

    public Registro search(String code, int hashType) {
        int index;

        if (hashType == 1) {
            index = hashDivision(code);
        } else if (hashType == 2) {
            index = hashMultiply(code);
        } else {
            index = hashFold(code);
        }

        ListaEncadeada<Registro> list = table[index];
        return list.find(code);
    }

    public int getCollisions() {
        return collisions;
    }

    public static void test(HashTable table, Registro[] registros, int hashType, int run) {
        long insertionTime;
        long startTime = System.currentTimeMillis();

        System.out.println("|Started: Mode: " + hashType + " | Table Size: " + table.size + " | Registros Size: " + registros.length);

        for (Registro reg : registros) {
            table.insert(reg, hashType);
        }

        insertionTime = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        for (Registro reg : registros) {
            table.search(reg.getCodigo(), hashType);
        }

        long searchTime = System.currentTimeMillis() - startTime;
        String logFilePath = "/home/bruno/Documents/Aula/estrutura/hash/final_logs.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) { 
            writer.write("@=========================@\n");
            writer.write("| Run: " + run);
            writer.write("| Hash Type: " + hashType + "\n");
            writer.write("| Table Size: " + table.size + "\n");
            writer.write("| Registros Size: " + registros.length + "\n");
            writer.write("| Insertion Time: " + insertionTime + "ms\n");
            writer.write("| Search Time: " + searchTime + "ms\n");
            writer.write("| Collisions: " + table.getCollisions() + "\n");
            writer.write("@=========================@\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }

        System.out.println("|Finished: Mode: " + hashType + " | Table Size: " + table.size + " | Registros Size: " + registros.length);

    }

}
