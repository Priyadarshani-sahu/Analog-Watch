package analogclock;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AnalogClock extends JFrame {

    public AnalogClock() {
        setTitle("Analog Clock");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        ClockPanel clock = new ClockPanel();
        add(clock);

        Timer timer = new Timer(1000, e -> {
            clock.setCurrentTime();
            clock.repaint();
        });

        timer.start();
        clock.setCurrentTime();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AnalogClock app = new AnalogClock();
            app.setVisible(true);
        });
    }
}

// Panel that draws the clock
class ClockPanel extends JPanel {
    private int centerX, centerY, clockRadius;

    public ClockPanel() {
        setBackground(Color.WHITE);
    }

    public void setCurrentTime() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        clockRadius = Math.min(getWidth(), getHeight()) / 2 - 20;
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        // Draw clock face
        g2d.setColor(new Color(37, 116, 50));
        g2d.fillOval(centerX - clockRadius, centerY - clockRadius, 2 * clockRadius, 2 * clockRadius);

        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(Color.WHITE);

        // Draw numbers
        for (int hour = 1; hour <= 12; hour++) {
            double angle = Math.toRadians(90 - (360 / 12) * hour);
            int x = (int) (centerX + clockRadius * 0.8 * Math.cos(angle));
            int y = (int) (centerY - clockRadius * 0.8 * Math.sin(angle));
            g2d.drawString(Integer.toString(hour), x - 7, y + 5);
        }

        // Get current time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        int hours = Integer.parseInt(currentTime.substring(0, 2)) % 12;
        int minutes = Integer.parseInt(currentTime.substring(3, 5));
        int seconds = Integer.parseInt(currentTime.substring(6, 8));

        // Calculate angles
        double hourAngle = Math.toRadians(90 - (360 / 12) * (hours + minutes / 60.0));
        double minuteAngle = Math.toRadians(90 - (360 / 60) * minutes);
        double secondAngle = Math.toRadians(90 - (360 / 60) * seconds);

        // Draw hands
        drawClockHand(g2d, clockRadius * 0.5, hourAngle, 6, Color.YELLOW);
        drawClockHand(g2d, clockRadius * 0.7, minuteAngle, 4, Color.YELLOW);
        drawClockHand(g2d, clockRadius * 0.8, secondAngle, 2, new Color(255, 90, 90));
    }

    private void drawClockHand(Graphics2D g2d, double length, double angle, int thickness, Color color) {
        int x2 = (int) (centerX + length * Math.cos(angle));
        int y2 = (int) (centerY - length * Math.sin(angle));

        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(color);
        g2d.drawLine(centerX, centerY, x2, y2);
    }
}
