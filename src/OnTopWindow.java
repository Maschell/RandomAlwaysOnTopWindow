import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class OnTopWindow {

    public static void main(String[] args) {
        new OnTopWindow();
    }

    public OnTopWindow() {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        frame.setLocationByPlatform(true);
        frame.add(new ColourPanel(Color.BLACK));
        frame.setSize(200, 100);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (e.getKeyCode() == NativeKeyEvent.VC_F4) {
                    new Thread(() -> {
                        frame.setVisible(false);
                        try {
                            Thread.sleep(1800);
                        } catch (InterruptedException e1) {
                        }
                        frame.setVisible(true);
                    }).start();
                }
            }

            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
            }
        });
    }

    class ColourPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public ColourPanel(Color colour) {
            super();
            this.setBackground(colour);
        }
    }
}