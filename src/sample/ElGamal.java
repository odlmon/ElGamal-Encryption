package sample;

import javafx.util.Pair;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class ElGamal {

    public static BigInteger fastExp(BigInteger a, BigInteger z, BigInteger n) {
        BigInteger x = BigInteger.ONE;
        while (z.compareTo(BigInteger.ZERO) != 0) {
            while (z.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) == 0) {
                z = z.divide(BigInteger.valueOf(2));
                a = a.multiply(a).mod(n);
            }
            z = z.subtract(BigInteger.ONE);
            x = x.multiply(a).mod(n);
        }
        return x;
    }

    public static ArrayList<BigInteger> getPrimeDividers(BigInteger num) {
        ArrayList<BigInteger> result = new ArrayList<>();
        for (BigInteger i = BigInteger.valueOf(2); i.multiply(i).compareTo(num) <= 0; i = i.add(BigInteger.ONE)) {
            if (num.mod(i).compareTo(BigInteger.ZERO) == 0) {
                result.add(i);
                while (num.mod(i).compareTo(BigInteger.ZERO) == 0)
                    num = num.divide(i);
            }
        }
        if (num.compareTo(BigInteger.ONE) != 0)
            result.add(num);
        return result;
    }

    public static ArrayList<BigInteger> getRoots(BigInteger p) {
        ArrayList<BigInteger> primeDividers = getPrimeDividers(p.subtract(BigInteger.ONE));
        ArrayList<BigInteger> roots = new ArrayList<>();
        for (BigInteger i = BigInteger.valueOf(2); i.compareTo(p) < 0; i = i.add(BigInteger.ONE)) {
            boolean isValid = true;
            for (BigInteger primeDivider : primeDividers) {
                if (fastExp(i, p.subtract(BigInteger.ONE).divide(primeDivider), p)//----------
                        .compareTo(BigInteger.ONE) == 0) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                roots.add(i);
            }
        }
        return roots;
    }

    public static boolean isPrime(BigInteger num) {
        boolean isPrime = true;
        for (BigInteger i = BigInteger.valueOf(2); i.multiply(i).compareTo(num) <= 0; i = i.add(BigInteger.ONE)) {
            if (num.mod(i).compareTo(BigInteger.ZERO) == 0) {
                isPrime = false;
                break;
            }
        }
        return (num.compareTo(BigInteger.ONE) > 0) && isPrime;
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        BigInteger temp;
        if (a.compareTo(b) < 0) {
            temp = a;
            a = b;
            b = temp;
        }
        while (b.compareTo(BigInteger.ZERO) > 0) {
            a = a.mod(b);
            temp = a;
            a = b;
            b = temp;
        }
        return a;
    }

    /*private static int getSecretValue(int p) {
        int from = 2;
        int to = p - 2;
        int k;
        do {
            k = from + (int) (Math.random() * to);
        } while (gcd(k, p - 1) != 1);
        return k;
    }*/

    public static Pair<BigInteger, BigInteger> encrypt(BigInteger m, BigInteger p, BigInteger g, BigInteger y, BigInteger k) {
        BigInteger a = fastExp(g, k, p);
        BigInteger b = fastExp(y, k, p).multiply(m.mod(p)).mod(p);
        return new Pair<>(a, b);
    }

    public static BigInteger decrypt(Pair<BigInteger, BigInteger> cypherText, BigInteger x, BigInteger p) {
        return ((cypherText.getValue().mod(p))
                .multiply(fastExp(cypherText.getKey(), p.subtract(BigInteger.ONE).subtract(x), p))).mod(p);
    }

    public static void encryptWithFiles(String plainFile, String cypherFile, BigInteger p, BigInteger g, BigInteger y,
                                         BigInteger k) {
        BigInteger plainText;
        Pair<BigInteger, BigInteger> cypherText;
        int amount = 0;
        try (BufferedInputStream bufferedInputStream =
                     new BufferedInputStream(new FileInputStream(plainFile));
             BufferedOutputStream bufferedOutputStream =
                     new BufferedOutputStream(new FileOutputStream(cypherFile))) {
            while (((plainText = BigInteger.valueOf(bufferedInputStream.read())).compareTo(BigInteger.valueOf(-1))) != 0) {
                cypherText = encrypt(plainText, p, g, y, k);
                if (amount < 200) {
                    System.out.print(cypherText.getKey() + " " + cypherText.getValue() + " ");
                    amount ++;
                }
                byte[] aPart = new byte[8], bPart = new byte[8];
                Arrays.fill(aPart, (byte) 0);
                Arrays.fill(bPart, (byte) 0);
                byte[] aTemp = cypherText.getKey().toByteArray();
                byte[] bTemp = cypherText.getValue().toByteArray();
                int counter = 0;
                for (int i = aPart.length - aTemp.length; i < 8; i++) {
                    aPart[i] = aTemp[counter++];
                }
                counter = 0;
                for (int i = bPart.length - bTemp.length; i < 8; i++) {
                    bPart[i] = bTemp[counter++];
                }
                bufferedOutputStream.write(aPart);
                bufferedOutputStream.write(bPart);
            }
        } catch (IOException ex) {
            System.out.println("Ошибка");
        }
    }

    public static void decryptWithFiles(String cypherFile, String plainFile, BigInteger x, BigInteger p) {
        BigInteger plainText;
        Pair<BigInteger, BigInteger> cypherText;
        byte[] inputArray = new byte[16];
        try (BufferedInputStream bufferedInputStream =
                     new BufferedInputStream(new FileInputStream(cypherFile), 16);
             BufferedOutputStream bufferedOutputStream =
                     new BufferedOutputStream(new FileOutputStream(plainFile))) {
            while (bufferedInputStream.read(inputArray, 0, 16) != -1) {
                cypherText = new Pair<>(new BigInteger(Arrays.copyOfRange(inputArray,0, 8)),
                        new BigInteger(Arrays.copyOfRange(inputArray, 8, 16)));
                plainText = decrypt(cypherText, x, p);
                bufferedOutputStream.write(plainText.byteValue());
            }
        } catch (IOException ex) {
            System.out.println("Ошибка");
        }
    }
}
