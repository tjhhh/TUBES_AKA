package com.mycompany.tubesaka;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TubesAKA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Deklarasi array
        int[] Id = new int[1008];
        String[] Country = new String[1008];
        String[] Firstname = new String[1008];
        String[] Lastname = new String[1008];
        String[] JenisKelamin = new String[1008];
        int[] Umur = new int[1008];
        String[] Kategori = new String[1008];
        String[] Survived = new String[1008];
        
        // Array untuk menyimpan salinan data setelah sorting iteratif
        int[] Id1 = new int[1008];
        String[] Country1 = new String[1008];
        String[] Firstname1 = new String[1008];
        String[] Lastname1 = new String[1008];
        String[] JenisKelamin1 = new String[1008];
        int[] Umur1 = new int[1008];
        String[] Kategori1 = new String[1008];
        String[] Survived1 = new String[1008];
        
        // Array untuk menyimpan salinan data setelah sorting rekursif
        int[] Id2 = new int[1008];
        String[] Country2 = new String[1008];
        String[] Firstname2 = new String[1008];
        String[] Lastname2 = new String[1008];
        String[] JenisKelamin2 = new String[1008];
        int[] Umur2 = new int[1008];
        String[] Kategori2 = new String[1008];
        String[] Survived2 = new String[1008];

        String filePath = "C://tumbal//tubesFile.csv"; // Lokasi file CSV Anda
        String line;
        String separator = ",";

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            br.readLine(); // Lewati baris pertama (header/keterangan)

            int index = 0; // Indeks untuk array
            while ((line = br.readLine()) != null && index < 1008) {
                String[] data = line.split(separator); // Pisahkan baris menjadi kolom

                // Memasukkan data ke array sesuai urutan
                Id[index] = Integer.parseInt(data[0].trim());           // Id
                Country[index] = data[1].trim();                       // Country
                Firstname[index] = data[2].trim();                     // Firstname
                Lastname[index] = data[3].trim();                      // Lastname
                JenisKelamin[index] = data[4].trim();                  // Jenis Kelamin
                Umur[index] = Integer.parseInt(data[5].trim());        // Umur
                Kategori[index] = data[6].trim();                      // Kategori
                Survived[index] = data[7].trim();                      // Survived

                index++;
            }
            br.close();

            // Menampilkan data sebelum sorting untuk memeriksa hasil
            System.out.println("Data sebelum sorting:");
            for (int i = 0; i < index; i++) {
                System.out.println("ID: " + Id[i] + ", Country: " + Country[i] + ", Firstname: " + Firstname[i] +
                                   ", Lastname: " + Lastname[i] + ", Jenis Kelamin: " + JenisKelamin[i] +
                                   ", Umur: " + Umur[i] + ", Kategori: " + Kategori[i] +
                                   ", Survived: " + Survived[i]);
            }

            // Input ukuran untuk sorting
            System.out.print("Masukan input size sorting: ");
            int masukan = scanner.nextInt();

            // Salin data ke array sementara sebelum sorting
            for (int i = 0; i < masukan; i++) {
                Id1[i] = Id[i];
                Country1[i] = Country[i];
                Firstname1[i] = Firstname[i];
                Lastname1[i] = Lastname[i];
                JenisKelamin1[i] = JenisKelamin[i];
                Umur1[i] = Umur[i];
                Kategori1[i] = Kategori[i];
                Survived1[i] = Survived[i];
            }

            // Sorting iteratif
            System.out.println("Sorting iteratif:");
            SelSortIteratif(masukan, Id1, Country1, Firstname1, Lastname1, JenisKelamin1, Umur1, Kategori1, Survived1);
            for (int i = 0; i < masukan; i++) {
                System.out.println("Firstname: " + Firstname1[i] + ", Umur: " + Umur1[i]);
            }
            // Salin hasil sorting iteratif ke array kedua untuk ditampilkan
            for (int i = 0; i < masukan; i++) {
                Id2[i] = Id1[i];
                Country2[i] = Country1[i];
                Firstname2[i] = Firstname1[i];
                Lastname2[i] = Lastname1[i];
                JenisKelamin2[i] = JenisKelamin1[i];
                Umur2[i] = Umur1[i];
                Kategori2[i] = Kategori1[i];
                Survived2[i] = Survived1[i];
            }

            // Sorting rekursif
            System.out.println(" ");
            System.out.println("Sorting rekursif:");
            SelSortRekursif(masukan, Id2, Country2, Firstname2, Lastname2, JenisKelamin2, Umur2, Kategori2, Survived2, 0);

            // Menampilkan hasil setelah sorting rekursif
            for (int i = 0; i < masukan; i++) {
                System.out.println("Firstname: " + Firstname2[i] + ", Umur: " + Umur2[i]);
            }

        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Format data tidak sesuai: " + e.getMessage());
        }
    }

    public static void SelSortIteratif(int input, int[] Id, String[] Country, String[] Firstname, String[] Lastname, String[] JenisKelamin, int[] Umur, String[] Kategori, String[] Survived) {
        int temp;
        String tempString;
        for (int i = 0; i < input - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < input; j++) {
                if (Umur[j] < Umur[minIndex]) {
                    minIndex = j;
                }
            }

            // Tukar data terkait
            temp = Umur[i];
            Umur[i] = Umur[minIndex];
            Umur[minIndex] = temp;

            temp = Id[i];
            Id[i] = Id[minIndex];
            Id[minIndex] = temp;

            tempString = Country[i];
            Country[i] = Country[minIndex];
            Country[minIndex] = tempString;

            tempString = Firstname[i];
            Firstname[i] = Firstname[minIndex];
            Firstname[minIndex] = tempString;

            tempString = Lastname[i];
            Lastname[i] = Lastname[minIndex];
            Lastname[minIndex] = tempString;

            tempString = JenisKelamin[i];
            JenisKelamin[i] = JenisKelamin[minIndex];
            JenisKelamin[minIndex] = tempString;

            tempString = Kategori[i];
            Kategori[i] = Kategori[minIndex];
            Kategori[minIndex] = tempString;

            tempString = Survived[i];
            Survived[i] = Survived[minIndex];
            Survived[minIndex] = tempString;
        }
    }

    public static void SelSortRekursif(int input, int[] Id, String[] Country, String[] Firstname, String[] Lastname, String[] JenisKelamin, int[] Umur, String[] Kategori, String[] Survived, int currentIndex) {
        if (currentIndex >= input - 1) {
            return; // selesai, jika currentIndex >= input - 1
        }

        // Mencari elemen terkecil
        int minIndex = currentIndex;
        for (int j = currentIndex + 1; j < input; j++) {
            if (Umur[j] < Umur[minIndex]) {
                minIndex = j;
            }
        }

        // Tukar data terkait
        int temp = Umur[currentIndex];
        Umur[currentIndex] = Umur[minIndex];
        Umur[minIndex] = temp;

        temp = Id[currentIndex];
        Id[currentIndex] = Id[minIndex];
        Id[minIndex] = temp;

        String tempString = Country[currentIndex];
        Country[currentIndex] = Country[minIndex];
        Country[minIndex] = tempString;

        tempString = Firstname[currentIndex];
        Firstname[currentIndex] = Firstname[minIndex];
        Firstname[minIndex] = tempString;

        tempString = Lastname[currentIndex];
        Lastname[currentIndex] = Lastname[minIndex];
        Lastname[minIndex] = tempString;

        tempString = JenisKelamin[currentIndex];
        JenisKelamin[currentIndex] = JenisKelamin[minIndex];
        JenisKelamin[minIndex] = tempString;

        tempString = Kategori[currentIndex];
        Kategori[currentIndex] = Kategori[minIndex];
        Kategori[minIndex] = tempString;

        tempString = Survived[currentIndex];
        Survived[currentIndex] = Survived[minIndex];
        Survived[minIndex] = tempString;

        // Panggil rekursif untuk elemen berikutnya
        SelSortRekursif(input, Id, Country, Firstname, Lastname, JenisKelamin, Umur, Kategori, Survived, currentIndex + 1);
    }
}


