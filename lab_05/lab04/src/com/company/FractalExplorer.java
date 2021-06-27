package com.company;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.filechooser.FileFilter;

public class FractalExplorer {
    final private int displaySize;
    public JImageDisplay display;
    public FractalGenerator fractal;
    final private Rectangle2D.Double range;

    public FractalExplorer(int size) {
        /** Сохраняет размер дисплея **/
        displaySize = size;

        /** Инициализирует фрактальный генератор и объекты диапазона **/
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
    }

    public void createAndShowGUI ()
    {
        JFrame myframe = new JFrame("Fractal Explorer");
        display.setLayout(new BorderLayout());
        myframe.add(display, BorderLayout.CENTER);
        JButton resetButton = new JButton("Reset Display");
        JButton buttonSave = new JButton("Save Image");
        ResetHandler handler = new ResetHandler();
        Save save = new Save();
        buttonSave.addActionListener(save);
        resetButton.addActionListener(handler);

        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myframe.pack();
        myframe.setVisible(true);
        myframe.setResizable(false);

        JLabel jLabel = new JLabel("Fractal: ");
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Mandelbrot");
        comboBox.addItem("Tricorn");
        comboBox.addItem("BurningShip");
        JPanel jpanel = new JPanel();



        jpanel.add(jLabel);
        jpanel.add(comboBox);
        myframe.getContentPane().add(jpanel, BorderLayout.NORTH);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameFractal = (String) comboBox.getSelectedItem();
                if (nameFractal.equals("Mandelbrot")){
                    fractal = new Mandelbrot();
                    fractal.getInitialRange(range);
                    drawFractal();
                }
                if (nameFractal.equals("Tricorn")){
                    fractal = new Tricorn();
                    fractal.getInitialRange(range);
                    drawFractal();
                }
                if (nameFractal.equals("BurningShip")){
                    fractal = new BurningShip();
                    fractal.getInitialRange(range);
                    drawFractal();
                }
            }
        });

        JPanel jpanelBoth = new JPanel();

        jpanelBoth.add(resetButton);
        jpanelBoth.add(buttonSave);
        myframe.getContentPane().add(jpanelBoth, BorderLayout.SOUTH);

        myframe.addMouseListener(click);
        myframe.getContentPane().add(display, BorderLayout.CENTER);
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myframe.getContentPane().add(jpanel, BorderLayout.NORTH);
        myframe.pack();
        myframe.setVisible(true);
        myframe.setResizable(false);


    }

    private void drawFractal()
    {
        // Перебираем каждую строку на дисплее и вызываем FractalWorker
        for (int x=0; x<displaySize; x++){
            FractalWorker drawRow = new FractalWorker(x);
            drawRow.execute();
        }

    }

    public class FractalWorker extends SwingWorker<Object, Object> {
        int yCoordinate;

        int[] computedRGBValues;

        private FractalWorker(int row) {
            yCoordinate = row;
        }
        @Override
        protected Object doInBackground() {

            computedRGBValues = new int[displaySize];

            // Перебрать все пиксели в строке.
            for (int i = 0; i < computedRGBValues.length; i++) {

                // Находим соответствующие координаты xCoord и yCoord
                // в области отображения фрактала.
                double xCoord = fractal.getCoord(range.x,
                        range.x + range.width, displaySize, i);
                double yCoord = fractal.getCoord(range.y,
                        range.y + range.height, displaySize, yCoordinate);

                // Вычислить количество итераций для координат в
                // область отображения фрактала.
                int iteration = fractal.numIterations(xCoord, yCoord);

                // Если количество итераций равно -1, установить текущий int в
                // вычислил массив значений RGB в черный цвет.
                if (iteration == -1) {
                    computedRGBValues[i] = 0;
                } else {
                    // В противном случае выберите значение оттенка на основе числа
                    // итераций.
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                    // Обновляем массив int цветом для
                    // текущий пиксель.
                    computedRGBValues[i] = rgbColor;
                }
            }
            return null;
        }

        protected void done() {
            // Обходим массив строковых данных, рисуя в пикселях
            // которые были вычислены в doInBackground ().
            for (int i = 0; i < computedRGBValues.length; i++) {
                display.drawPixel(i, yCoordinate, computedRGBValues[i]);
            }
            display.repaint(0, 0, yCoordinate, displaySize, 1);
        }
    }


    private class ResetHandler implements ActionListener{
        /**
         * Обработчик сбрасывает диапазон до начального диапазона, заданного параметром
         * генератор, а затем рисует фрактал.
         */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            fractal.getInitialRange(range);
            drawFractal();
        }
    }

    private class Save implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("PNG Images", "png");
            fileChooser.setFileFilter(fileFilter);

            int t = fileChooser.showSaveDialog(display);
            if (t == JFileChooser.APPROVE_OPTION) {
                try {
                    ImageIO.write(display.getImage(), "png", fileChooser.getSelectedFile());
                } catch (NullPointerException | IOException ee) {
                    JOptionPane.showMessageDialog(display, ee.getMessage(), "Cannot save image", JOptionPane.ERROR_MESSAGE);
                }
            }
            JFileChooser jFileChooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
            jFileChooser.setFileFilter(filter);
            jFileChooser.setAcceptAllFileFilterUsed(false);
        }

    }

    private class MouseHandler extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x,
                    range.x + range.width, displaySize, x);

            int y = e.getY();
            double yCoord = fractal.getCoord(range.y,
                    range.y + range.height, displaySize, y);

            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }


    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(900);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}
