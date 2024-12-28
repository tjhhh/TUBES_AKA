package com.mycompany.tubesaka;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;

class Passenger {
    int id;
    String country;
    String firstname;
    String lastname;
    String jenisKelamin;
    int umur;
    String kategori;
    String survived;

    public Passenger(int id, String country, String firstname, String lastname, String jenisKelamin, int umur, String kategori, String survived) {
        this.id = id;
        this.country = country;
        this.firstname = firstname;
        this.lastname = lastname;
        this.jenisKelamin = jenisKelamin;
        this.umur = umur;
        this.kategori = kategori;
        this.survived = survived;
    }
}

public class TubesAKA_GUI {

    static Passenger[] passengers = new Passenger[1008];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Sorting Algorithm");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel label = new JLabel("Masukan ukuran input sorting: ");
        JTextField inputField = new JTextField(5);
        JButton runButton = new JButton("Run");

        inputPanel.add(label);
        inputPanel.add(inputField);
        inputPanel.add(runButton);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(2, 1));

        JTextArea iterativeResult = new JTextArea(20, 30);
        iterativeResult.setEditable(false);
        iterativeResult.setText("Hasil Sorting Iteratif");

        JTextArea recursiveResult = new JTextArea(20, 30);
        recursiveResult.setEditable(false);
        recursiveResult.setText("Hasil Sorting Rekursif");

        JScrollPane scrollIterative = new JScrollPane(iterativeResult);
        JScrollPane scrollRecursive = new JScrollPane(recursiveResult);

        resultPanel.add(scrollIterative);
        resultPanel.add(scrollRecursive);

        JPanel chartPanel = new JPanel(new BorderLayout());
        JLabel chartLabel = new JLabel("Perbandingan Running Time", JLabel.CENTER);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createLineChart(
                "Running Time",
                "Ukuran Input",
                "Waktu Eksekusi (ms)",
                dataset
        );
        ChartPanel chartContainer = new ChartPanel(chart);

        chartPanel.add(chartLabel, BorderLayout.NORTH);
        chartPanel.add(chartContainer, BorderLayout.CENTER);

        JPanel tablePanel = new JPanel(new BorderLayout());
        JTable table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Ukuran Input", "Waktu Iteratif (ms)", "Waktu Rekursif (ms)"}, 0);
        table.setModel(tableModel);
        JScrollPane scrollTable = new JScrollPane(table);

        tablePanel.add(new JLabel("Tabel Hasil", JLabel.CENTER), BorderLayout.NORTH);
        tablePanel.add(scrollTable, BorderLayout.CENTER);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(resultPanel, BorderLayout.CENTER);
        frame.add(chartPanel, BorderLayout.EAST);
        frame.add(tablePanel, BorderLayout.SOUTH);

        runButton.addActionListener(e -> {
            try {
                readFileAndFillArrays();

                int masukan = Integer.parseInt(inputField.getText());
                if (masukan > passengers.length) {
                    JOptionPane.showMessageDialog(frame, "Ukuran input terlalu besar!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Menghitung waktu untuk sorting iteratif
                Passenger[] passengersIterative = passengers.clone();
                long startTimeIterative = System.nanoTime();
                SelSortIteratif(masukan, passengersIterative);
                long endTimeIterative = System.nanoTime();
                double durationIterative = (endTimeIterative - startTimeIterative) / 1_000_000.0; // Konversi ke milidetik

                // Menampilkan hasil sorting iteratif
                StringBuilder iterativeText = new StringBuilder();
                for (int i = 0; i < masukan; i++) {
                    iterativeText.append("Firstname: ").append(passengersIterative[i].firstname).append(", Umur: ").append(passengersIterative[i].umur).append("\n");
                }
                iterativeResult.setText(iterativeText.toString());

                DecimalFormat df = new DecimalFormat("#.###");
                dataset.addValue(durationIterative, "Iteratif", String.valueOf(masukan));
                tableModel.addRow(new Object[]{masukan, df.format(durationIterative) + " ms", ""});

                // Menghitung waktu untuk sorting rekursif
                Passenger[] passengersRecursive = passengers.clone();
                long startTimeRecursive = System.nanoTime();
                SelSortRekursif(masukan, passengersRecursive, 0, 1, masukan);
                long endTimeRecursive = System.nanoTime();
                double durationRecursive = (endTimeRecursive - startTimeRecursive) / 1_000_000.0; // Konversi ke milidetik

                // Menampilkan hasil sorting rekursif
                StringBuilder recursiveText = new StringBuilder();
                for (int i = 0; i < masukan; i++) {
                    recursiveText.append("Firstname: ").append(passengersRecursive[i].firstname).append(", Umur: ").append(passengersRecursive[i].umur).append("\n");
                }
                recursiveResult.setText(recursiveText.toString());

                dataset.addValue(durationRecursive, "Rekursif", String.valueOf(masukan));
                tableModel.setValueAt(df.format(durationRecursive) + " ms", tableModel.getRowCount() - 1, 2);

                // Logging untuk debugging
                System.out.println("Waktu Iteratif: " + durationIterative + " ms");
                System.out.println("Waktu Rekursif: " + durationRecursive + " ms");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    public static void readFileAndFillArrays() throws IOException {
        String filePath = "C:\\Users\\nhqkb\\OneDrive\\Documents\\CSV\\tubes.csv";
        String line;
        String separator = ",";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        br.readLine();

        int index = 0;
        while ((line = br.readLine()) != null && index < passengers.length) {
            String[] data = line.split(separator);
            passengers[index] = new Passenger(
                    Integer.parseInt(data[0].trim()),
                    data[1].trim(),
                    data[2].trim(),
                    data[3].trim(),
                    data[4].trim(),
                    Integer.parseInt(data[5].trim()),
                    data[6].trim(),
                    data[7].trim()
            );
            index++;
        }
        br.close();
    }

    public static void SelSortIteratif(int input, Passenger[] passengers) {
        for (int i = 0; i < input - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < input; j++) {
                if (passengers[j].umur < passengers[minIndex].umur) {
                    minIndex = j;
                }
            }
            Passenger temp = passengers[i];
            passengers[i] = passengers[minIndex];
            passengers[minIndex] = temp;
        }
    }

    public static void SelSortRekursif(int input, Passenger[] passengers, int currentIndex, int nextIndex, int idxInput) {
        if (idxInput == 0) {
            return; // Basis kasus
        }

        if (nextIndex < input) {
            // Bandingkan elemen currentIndex dengan nextIndex
            if (passengers[nextIndex].umur < passengers[currentIndex].umur) {
                Passenger temp = passengers[currentIndex];
                passengers[currentIndex] = passengers[nextIndex];
                passengers[nextIndex] = temp;
            }
            SelSortRekursif(input, passengers, currentIndex, nextIndex + 1, idxInput);
        } else {
            // Pindah ke elemen berikutnya
            SelSortRekursif(input, passengers, currentIndex + 1, currentIndex + 2, idxInput - 1);
        }
    }
}
