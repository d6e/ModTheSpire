package com.evacipated.cardcrawl.modthespire;

import com.evacipated.cardcrawl.modthespire.steam.SteamSearch;
import com.evacipated.cardcrawl.modthespire.ui.ModSelectWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SwingUtil {
    private static ModSelectWindow ex;
    public static ModInfo[] ALLMODINFOS;

    public enum ICON_TYPE { ERROR_MESSAGE };


    public static void showMessageDialog(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    public static void showMessageDialog(String title, String msg, ICON_TYPE icon) {
        if (icon == ICON_TYPE.ERROR_MESSAGE){
            JOptionPane.showMessageDialog(null, title, msg, JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void checkForMTSUpdate(boolean skipLauncher, List<SteamSearch.WorkshopInfo> workshopInfos) {
        EventQueue.invokeLater(() -> {
            ALLMODINFOS = Loader.getAllMods(workshopInfos);
            ex = new ModSelectWindow(ALLMODINFOS, skipLauncher);
            ex.setVisible(true);

            ex.warnAboutMissingVersions();

            String java_version = System.getProperty("java.version");
            if (!java_version.startsWith("1.8")) {
                String msg = "ModTheSpire requires Java version 8 to run properly.\nYou are currently using Java " + java_version;
                JOptionPane.showMessageDialog(null, msg, "Warning", JOptionPane.WARNING_MESSAGE);
            }

            ex.startCheckingForMTSUpdate();
        });
    }
    public static void closeWindow()
    {
        ex.dispatchEvent(new WindowEvent(ex, WindowEvent.WINDOW_CLOSING));
    }

    public static void iconifyFrame(){
        new Timer().schedule(
            new TimerTask()
            {
                @Override
                public void run()
                {
                    ex.setState(Frame.ICONIFIED);
                }
            },
            1000
        );
    }
}
