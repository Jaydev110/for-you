import java.util.Scanner;

public class HammingCodeFinal {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // =========================
        // SENDER SIDE
        // =========================

        // STEP 1
        System.out.print("Enter the data (binary): ");
        String data = sc.next();
        int m = data.length();

        System.out.println("\nStep 1: Given Data: " + data);
        System.out.println("Length of data (m): " + m);

        // STEP 2
        int r = 0;
        System.out.println("\nStep 2: Finding number of extra bits (r)");
        System.out.println("Condition: 2^r >= m + r + 1");

        while (Math.pow(2, r) < (m + r + 1)) {
            System.out.println("For r = " + r + " → 2^" + r + " = " + (int) Math.pow(2, r)
                    + " < " + (m + r + 1));
            r++;
        }

        System.out.println("For r = " + r + " → 2^" + r + " = " + (int) Math.pow(2, r)
                + " >= " + (m + r + 1));
        System.out.println("Number of redundant bits (r): " + r);

        // STEP 3
        int totalBits = m + r;
        System.out.println("\nStep 3: Total bits in Hamming Code (m + r): " + totalBits);

        // STEP 4
        System.out.println("\nStep 4: Positions of redundant bits (2^n)");
        for (int i = 0; i < r; i++) {
            int pos = 1 << i;
            System.out.println("R" + pos + " at position " + pos);
        }

        // STEP 5
        System.out.println("\nStep 5: Original Data Bits:\n");

        for (int i = 1; i <= m; i++) {
            System.out.print("D" + i + "  ");
        }
        System.out.println();

        for (int i = m - 1; i >= 0; i--) {
            System.out.print(data.charAt(i) + "   ");
        }
        System.out.println();

        // STEP 6
        String[] structure = new String[totalBits + 1];
        int dIndex = 1;

        for (int i = 1; i <= totalBits; i++) {
            if ((i & (i - 1)) == 0) {
                structure[i] = "R" + i;
            } else {
                structure[i] = "D" + dIndex++;
            }
        }

        System.out.println("\nStep 6: Hamming Code Structure (R = redundant, D = data)\n");
        for (int i = totalBits; i >= 1; i--) {
            System.out.print(structure[i] + "  ");
        }
        System.out.println();

        // STEP 7
        char[] hamming = new char[totalBits + 1];
        int dataIndex = m - 1;

        for (int i = 1; i <= totalBits; i++) {
            if ((i & (i - 1)) == 0) {
                hamming[i] = '0'; // placeholder
            } else {
                hamming[i] = data.charAt(dataIndex--);
            }
        }

        System.out.println("\nStep 7: Structure after placing data bits:\n");

        System.out.print("Position: ");
        for (int i = totalBits; i >= 1; i--) {
            System.out.print(i + "  ");
        }
        System.out.println();

        System.out.print("Label:    ");
        for (int i = totalBits; i >= 1; i--) {
            System.out.print(structure[i] + " ");
        }
        System.out.println();

        System.out.print("Values:   ");
        for (int i = totalBits; i >= 1; i--) {
            if ((i & (i - 1)) == 0) {
                System.out.print("R" + i + "  ");   // show R instead of 0
            } else {
                System.out.print(hamming[i] + "   ");
            }
        }
        System.out.println();

        // STEP 8
        System.out.println("\nStep 8: Truth Table (2^r rows)\n");
        int rows = (int) Math.pow(2, r);

        for (int i = 0; i < rows; i++) {
            for (int k = r - 1; k >= 0; k--) {
                System.out.print(((i >> k) & 1) + " ");
            }
            System.out.println();
        }

        // STEP 9
        System.out.println("\nStep 9: Redundant Bit Calculations\n");

        for (int i = 0; i < r; i++) {
            int pos = 1 << i;

            System.out.print("R" + pos + " = ");

            int count = 0;
            boolean first = true;

            for (int k = 1; k <= totalBits; k++) {
                if (((k >> i) & 1) == 1 && k != pos) {
                    if (!first) System.out.print(" XOR ");
                    System.out.print(hamming[k]);
                    if (hamming[k] == '1') count++;
                    first = false;
                }
            }

            int result = (count % 2 == 0) ? 0 : 1;

            System.out.println("\nR" + pos + " = " + result);

            hamming[pos] = (char) (result + '0');
        }

        // STEP 10
        System.out.println("\nStep 10: Final Hamming Code\n");
        for (int i = totalBits; i >= 1; i--) {
            System.out.print(hamming[i]);
        }

        System.out.println("\n\nHamming code transmitted successfully!");

        // =========================
        // RECEIVER SIDE
        // =========================

        System.out.print("\n\nEnter received Hamming code: ");
        String received = sc.next();

        int n = received.length();

        System.out.println("\nStep 1: Received Data: " + received);
        System.out.println("Length (m + r): " + n);

        int rcv_r = 0;
        while (Math.pow(2, rcv_r) < (n + 1)) {
            rcv_r++;
        }
        System.out.println("Redundant bits (r): " + rcv_r);

        int m_original = n - rcv_r;
        System.out.println("Original data length (m): " + m_original);

        char[] rec = new char[n + 1];
        int idx = n - 1;
        for (int i = 1; i <= n; i++) {
            rec[i] = received.charAt(idx--);
        }

        int errorPos = 0;
        String binaryError = "";

        for (int i = 0; i < rcv_r; i++) {
            int count = 0;

            for (int k = 1; k <= n; k++) {
                if (((k >> i) & 1) == 1) {
                    if (rec[k] == '1') count++;
                }
            }

            int parity = count % 2;
            binaryError = parity + binaryError;

            if (parity != 0) {
                errorPos += (1 << i);
            }
        }

        System.out.println("\nBinary Error Position: " + binaryError);
        System.out.println("Decimal Error Position: " + errorPos);

        if (errorPos != 0) {
            System.out.println("\nError detected at position: " + errorPos);

            rec[errorPos] = (rec[errorPos] == '0') ? '1' : '0';

            System.out.print("Corrected Code: ");
            for (int i = n; i >= 1; i--) {
                System.out.print(rec[i]);
            }
            System.out.println();
        } else {
            System.out.println("\nNo error detected. The received data is correct.");
        }

        System.out.println("\nRedundant bit positions:");
        for (int i = 0; i < rcv_r; i++) {
            System.out.println(1 << i);
        }

        System.out.print("\nOriginal Data by removing redundant bits: ");
        for (int i = n; i >= 1; i--) {
            if ((i & (i - 1)) != 0) {
                System.out.print(rec[i]);
            }
        }

        System.out.println();

        sc.close();
    }
}