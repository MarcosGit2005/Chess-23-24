package ieslavereda;

import java.io.*;
import java.math.BigDecimal;

public class CopyObjects {
    static String[] productos = {"manzana","pera","aaaaaaaaaaaaaaaaaaaaaaaaaaaa"};
    static BigDecimal[] precios = {new BigDecimal(5.34),new BigDecimal(432.6),new BigDecimal(0.13)};
    static Integer[] disponibilidad = {100,200,300};

    public static void main(String[] args) {
        try {
            save();
            load();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static void save() throws IOException{
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("objetos.dat")
                )
        )){
            for (int i = 0; i < productos.length; i++) {
                objectOutputStream.writeObject(productos[i]);
                objectOutputStream.writeObject(precios[i]);
                objectOutputStream.writeObject(disponibilidad[i]);
            }
        }
    }
    public static void load() throws IOException, ClassNotFoundException{
        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream("objetos.dat")
                )
        )){
            String producto;
            BigDecimal precio;
            Integer disponibilidad;

            while (true){
                producto = (String) objectInputStream.readObject();
                precio = (BigDecimal) objectInputStream.readObject();
                disponibilidad = (Integer) objectInputStream.readObject();
                System.out.println("Producto: " + producto + ", precio: " + precio + ", disponibilidad: " + disponibilidad);
            }
        } catch (EOFException e){
        }
    }
}
