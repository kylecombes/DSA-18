import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;

public class testHashMap {

    public testHashMap() {
        int i = 0;
    }


    public void main(String[] args) {
        HashMap<BitSet, Integer> map = new HashMap<>();
        FileOutputStream fwriter;
        try {
            fwriter = new FileOutputStream("sample");
            ObjectOutputStream writer = new ObjectOutputStream(fwriter);

            writer.writeObject(map);

            writer.close(); //don't forget to close the writer
        }
        catch (Exception e){
//            fwriter = new FileOutputStream("sample", 1);
        }


    }

}
