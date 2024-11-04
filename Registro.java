import java.util.Random;

public class Registro {

    private String codigo;  
    
    public Registro(String codigo) {
        this.codigo = codigo;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public static Registro[] build(int size) {

        Random random = new Random(200);

        Registro[] data = new Registro[size];

        for (int i = 0; i < size; i++) {

            data[i] = new Registro(
                String.format(
                    "%09d", 
                    random.nextInt(1_000_000_000)
                )
            );

        }

        return data;

    }

}
