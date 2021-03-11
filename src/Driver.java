import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    FileOutputStream fout; // declare and create an output stream
    ArrayList<Integer> inputData; // all keys from the input file

    // Print data and heap
    public void printHeap(final String description, final Heap<Integer> heap) {
        System.out.print(description + "\n");
        System.out.print("[" + heap.size() + "]");

        for (int i = 0; i < heap.size(); i++) {
            System.out.print(" " + heap.getElement(i));
        }
        System.out.print("\n");
    }

    // Consider heap elements ordered if first <= second or second is beyond heap size
    public boolean areHeapElementsOrdered(final Heap<Integer> heap, final int i, final int j) {
        return i >= heap.size() || j >= heap.size() || heap.getElement(i) <= heap.getElement(j); // Edit this line
    }

    // Returns stringized element or "none" if beyond heap size
    public String heapElement(final Heap<Integer> heap, final int i) {
        return i < heap.size() ? heap.getElement(i).toString() : "none";
    }

    // Check that it is really a heap, throw an exception otherwise
    public void checkHeap(final Heap<Integer> heap) {
        for (int i = 0; i < heap.size(); i++) {
            if (areHeapElementsOrdered(heap, i, i * 2 + 1) && areHeapElementsOrdered(heap, i, i * 2 + 2)) {
                continue;
            } else {
                // else is added
                printHeap("Corrupted", heap);

                System.out.print("Error: heap violation at index " + i + ", heap[" + i + "] = " + heapElement(heap, i) + ", heap[" + (i * 2 + 1) + "] = " + heapElement(heap, i * 2 + 1) + ", heap[" + (i * 2 + 2) + "] = " + heapElement(heap, i * 2 + 2) + "\n");
                throw new RuntimeException("Not a heap");
            }
        }
    }

    // Insert one element, check, and print
    public void insertOne(final Heap<Integer> heap, int element) {
        System.out.print("Insert " + element + "\n");

        heap.insert(element);
        checkHeap(heap);

        printHeap("Heap after insert " + element, heap);
    }

    // Delete minimum element, check, and print
    public void deleteOne(final Heap<Integer> heap) {
        System.out.print("Delete " + heap.getElement(0) + "\n");

        heap.deleteMin();
        checkHeap(heap);
    }

    // Test heap functions
    // - insert all input elements
    // - insert 31 and 14
    // - delete all min elements
    public void testData() {
        Heap<Integer> heap = null; // = null?

        for (int key : inputData) {
            heap.insert(key);
            checkHeap(heap);
        }
        printHeap("Heap", heap);

        insertOne(heap, 31);
        printHeap("Heap after insert 31", heap);

        insertOne(heap, 14);
        printHeap("Heap after insert 14", heap);

        while (heap.size() > 0) {
            deleteOne(heap);
            printHeap("Heap after deleteOne", heap);
        }
    }

    // Read data from the Integers from the input file
    public void readData(final String inputFile) throws FileNotFoundException {
        Scanner fin = new Scanner(new File(inputFile));
        Integer key;

        System.out.print("Input data");
        while (fin.hasNextInt()) {
            key = fin.nextInt();

            inputData.add(key);
            System.out.print(key);
        }
        System.out.print("\n");

        fin.close();
    }

    // Test the input file, print result to the output file
    public void testFile(final String inputFile, final String outputFile) throws IOException {
        PrintStream original = new PrintStream(System.out);
        fout = new FileOutputStream(outputFile);
        PrintStream ps = new PrintStream(fout);

        readData(inputFile);

        fout.close();
    }

    public Driver() {
        try {
           for (int i = 1; i <= 4; i ++) {
               final String suffix = i + ".txt";
               testFile("in" + suffix, "out" + suffix);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Driver();
    }
}
