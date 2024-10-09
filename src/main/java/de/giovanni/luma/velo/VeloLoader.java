package de.giovanni.luma.velo;

import de.byteandbit.velociraptor.api.VelociraptorAPI;

public class VeloLoader {

    public static VelociraptorAPI API;

    public static void load(Runnable callback) {
        new Thread(() -> {
            try {
                Thread.sleep(10000);
                boolean foundApi = false;

                while (!foundApi) {
                    System.out.println("SUCHE VELOCIRAPTOR API.....");

                    try {
                        Class.forName("de.byteandbit.velociraptor.api.VelociraptorAPI");
                        foundApi = true;
                    } catch (Exception ex) {}
                    Thread.sleep(1000L);
                }

                API = new VelociraptorAPI();
                System.out.println("Velociraptor NG API wurde erfolgreich eingebunden!");
                callback.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}