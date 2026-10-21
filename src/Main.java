
import ArrayList.CustomArrayList;
import ArrayList.MyArrayList;
import RedBlackTree.MyRedBlackTree;
import pojo.Bin;
import pojo.Parcel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        CustomArrayList<Parcel<Integer>> parcelList = new CustomArrayList<>(100);

        try (BufferedReader br = new BufferedReader(new FileReader("./resources/random_truck_integer.txt"))) {
            String line;
            for (int i = 1; i <= 100; i++) {
                line = br.readLine();
                String[] words = line.split(",");
                parcelList.add(new Parcel<>(i, Integer.valueOf(words[1]), words[2], Integer.valueOf(words[3])));
            }
        } catch (IOException e) {
            System.out.println("Error While Opening File. ");
            e.printStackTrace();
        }

        BestFitTest<Integer, PriorityQueue<Bin<Integer>>> bestFitTest = new BestFitTest<>();
        bestFitTest.BestFitPacking(600, parcelList);

        FirstFitTest<Integer, Bin<Integer>> firstFitTest = new FirstFitTest<>();
        firstFitTest.FirstFitPacking(600, parcelList);
    }

}