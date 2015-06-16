/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.math3.stat.StatUtils;


/**
 *
 * @author linh
 */
public class ChartPanel extends JPanel {

    private double[] values;
    private final String[] names = new String[]{"0.05", "0.1", "0.2", "0.4", "0.8", "1.0"};
    private String title;

    public ChartPanel(double[] v, String t) {
        values = v;
        title = t;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (values == null || values.length == 0) {
            return;
        }
        double minValue = 0;
        double maxValue = 0;
        for (int i = 0; i < values.length; i++) {
            if (minValue > values[i]) {
                minValue = values[i];
            }
            if (maxValue < values[i]) {
                maxValue = values[i];
            }
        }

        Dimension d = getSize();
        int clientWidth = d.width;
        int clientHeight = d.height;
        int barWidth = clientWidth / values.length;

        Font titleFont = new Font("SansSerif", Font.BOLD, 20);
        FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
        FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

        int titleWidth = titleFontMetrics.stringWidth(title);
        int y = titleFontMetrics.getAscent();
        int x = (clientWidth - titleWidth) / 2;
        g.setFont(titleFont);
        g.drawString(title, x, y);

        int top = titleFontMetrics.getHeight();
        int bottom = labelFontMetrics.getHeight();
        if (maxValue == minValue) {
            return;
        }
        double scale = (clientHeight - top - bottom) / (maxValue - minValue);
        y = clientHeight - labelFontMetrics.getDescent();
        g.setFont(labelFont);


        // Them thac
        int idx = 0;
        double sum = StatUtils.sum(values);
        double accum = 0d;

        for (int i = 0; i < values.length; i++) {
            accum += values[i];
            int valueX = i * barWidth + 1;
            int valueY = top;
            int height = (int) (values[i] * scale);
            if (values[i] >= 0) {
                valueY += (int) ((maxValue - values[i]) * scale);
            } else {
                valueY += (int) (maxValue * scale);
                height = -height;
            }

            g.setColor(Color.red);
            g.fillRect(valueX, valueY, barWidth - 2, height);
            g.setColor(Color.black);
            g.drawRect(valueX, valueY, barWidth - 2, height);

            double percent = Double.parseDouble(names[idx]);
            if (i < values.length - 1 && percent < (accum + values[i + 1]) / sum 
                    && idx<names.length) {
                int labelWidth = labelFontMetrics.stringWidth(names[idx]);
                x = i * barWidth + (barWidth - labelWidth) / 2;
                g.drawString(names[idx], x, y);
                idx++;
            }
        }
    }
}