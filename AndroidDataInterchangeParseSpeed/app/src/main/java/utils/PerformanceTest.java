package utils;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class PerformanceTest {

    private final long measurments[][][];
    private final long memoryUsage[];
    private int measurmentCountIndex = 0;
    private int saveThreshold = 0;
    private final String fileName;
    private Context ctx;
    private boolean saveOnce = true;

    public PerformanceTest(String fileName, int sThreshold,Context ctx) {
        this.fileName = fileName;
        this.saveThreshold = sThreshold;
        measurments = new long[sThreshold + 2][5][2];
        memoryUsage = new long[sThreshold + 2];
        this.ctx = ctx;
    }

    public void tagStartTime(int i) {
        measurments[measurmentCountIndex][i][0] = System.nanoTime();
    }

    public void tagEndTime(int i) {
        measurments[measurmentCountIndex][i][1] = System.nanoTime();

    }

    public void nextMeasurment() {
        memoryUsage[measurmentCountIndex] = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        measurmentCountIndex++;
    }

    public void saveMeasurmentsToDisk() {

        if (saveThreshold <= measurmentCountIndex) {
            try {
                PrintWriter out = new PrintWriter(new FileWriter(new File(ctx.getExternalFilesDir(null)+"/"+fileName)));


                for (int i = 0; i <= measurmentCountIndex; i++) {
                    out.append((i + 1) + ",");
                    for (int j = 0; j < 5; j++) {
                        out.append(String.valueOf(measurments[i][j][1] - measurments[i][j][0]) + ",");
                    }
                    out.append(String.valueOf(memoryUsage[i]));
                    out.append("\n");
                }

                out.close();

                System.out.println("Performance test successfully acomplished! \nExecuted: " + measurmentCountIndex + " interations. \nResults saved in: " + fileName);
                measurmentCountIndex = 0;


            } catch (Exception ex) {
                System.err.println(ex + "\n\n Error while saving measurment Data!");
            }
        }

    }

}
