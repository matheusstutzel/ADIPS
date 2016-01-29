package javaxjparsespeed;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Domain.Person;
import utils.PerformanceTest;

public class ParseTests {

    private static ObjectMapper mapper = new ObjectMapper();
    public int forceHotSpot = 1000;
    public PerformanceTest  jacksonTest;
    public PerformanceTest  serialize64bitStringTest;
    public PerformanceTest  gsonTest;
    public PerformanceTest  boonTest;
    public PerformanceTest  XPP3xstreamXML;
    public PerformanceTest  DOMDriverXstreamXML;
    public PerformanceTest  StaxDriverXstreamXML;
    int threads = Runtime.getRuntime().availableProcessors();
    ExecutorService exec = Executors.newFixedThreadPool(threads);
    public ParseTests(Context ctx) {
        jacksonTest = new PerformanceTest("jacksonResults", forceHotSpot,ctx);
        serialize64bitStringTest = new PerformanceTest("serialize64bitStringTestResults", forceHotSpot,ctx);
        gsonTest = new PerformanceTest("gsonResults", forceHotSpot,ctx);
        boonTest = new PerformanceTest("boonTestResults", forceHotSpot,ctx);
        XPP3xstreamXML = new PerformanceTest("XPP3xstreamXMLResults", forceHotSpot,ctx);
        DOMDriverXstreamXML = new PerformanceTest("DOMDriverXstreamXMLResults", forceHotSpot,ctx);
        StaxDriverXstreamXML = new PerformanceTest("StaxDriverXstreamXMLResults", forceHotSpot,ctx);
    }

    public void parallelFor(final PerformanceTest p, final MyRunnable r) {
        for (int i = 0; i < forceHotSpot; i++) {
            final int j = i;

            exec.submit(new Runnable() {

                public void run() {
                    p.tagStartTime(j);

                    try {
                        //String threadName = Thread.currentThread().getName();
                        //System.out.println(threadName);

                        r.run(j);

                    } catch (Exception ex) {
                        System.err.println(" error");
                    } finally {
                        System.err.println("iteration:" + j);
                        p.tagEndTime(j);
                        p.nextMeasurment(j);
                    }
                }
            });


        }

        try {
            System.out.println("attempt to shutdown executor");
            exec.shutdown();
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }

        p.saveMeasurmentsToDisk();
    }

/*    public void jsonParserAsJackson(Person p) {

        for (int i = 0; i < forceHotSpot; i++) {
            jacksonTest.tagStartTime(0);
            try {

                String bookJson = mapper.writeValueAsString(p);
                Person personReborn = mapper.readValue(bookJson, Person.class);

            } catch (IOException iOException) {
                System.err.println(" error");
            }
            jacksonTest.tagEndTime(0);
            jacksonTest.nextMeasurment();
        }
        jacksonTest.saveMeasurmentsToDisk();
    }*/

/*    public void serializeObject(Person p) {

        for (int i = 0; i < forceHotSpot; i++) {
            serialize64bitStringTest.tagStartTime(0);
            try {

                String temp = toString(p);
                Person personReborn = (Person) fromString(temp);

            } catch (Exception iOException) {
                System.err.println("Serializing error");
            }

            serialize64bitStringTest.tagEndTime(0);
            serialize64bitStringTest.nextMeasurment();
        }
        serialize64bitStringTest.saveMeasurmentsToDisk();
    }*/

/*    public void jsonParserAsGson(Person p) {

        Gson gson = new GsonBuilder().create();
        for (int i = 0; i < forceHotSpot; i++) {
            gsonTest.tagStartTime(0);
            String temp = gson.toJson(p);
            if (i == 10000) {
                try {
                    System.err.println("Sleep begin 500");
                    Thread.sleep(20000);
                    System.err.println("Sleep end");

                } catch (Exception ex) {
                    System.err.println("thread error");
                }
            }
            Person personReborn = gson.fromJson(temp, Person.class);
            gsonTest.tagEndTime(0);
            gsonTest.nextMeasurment();
        }
        gsonTest.saveMeasurmentsToDisk();
    }*/
public void jsonParserAsJacksonParallel(final Person p) {

    parallelFor(jacksonTest, new MyRunnable() {
        @Override
        public void run(int i) throws Exception {
            String bookJson = mapper.writeValueAsString(p);
            Person personReborn = mapper.readValue(bookJson, Person.class);
        }
    });
}
/*
    public void xmlParserAsXpp3XStream(Person p) {
        XStream xstream = new XStream(new Xpp3Driver());
        for (int i = 0; i < forceHotSpot; i++) {
            XPP3xstreamXML.tagStartTime(0);
            String temp = xstream.toXML(p);
            Person personReborn = (Person) xstream.fromXML(temp);
            XPP3xstreamXML.tagEndTime(0);
            XPP3xstreamXML.nextMeasurment();
        }
        XPP3xstreamXML.saveMeasurmentsToDisk();
    }

    public void xmlParserAsDomDriverXStream(Person p) {
        XStream xstream = new XStream(new DomDriver());
        for (int i = 0; i < forceHotSpot; i++) {
            DOMDriverXstreamXML.tagStartTime(0);
            String temp = xstream.toXML(p);
            Person personReborn = (Person) xstream.fromXML(temp);
            DOMDriverXstreamXML.tagEndTime(0);
            DOMDriverXstreamXML.nextMeasurment();
        }
        DOMDriverXstreamXML.saveMeasurmentsToDisk();
    }

    public void xmlParserAsStaxDriverXStream(Person p) {

        XStream xstream = new XStream(new StaxDriver());

        for (int i = 0; i < forceHotSpot; i++) {
            StaxDriverXstreamXML.tagStartTime(0);
            String temp = xstream.toXML(p);
            Person personReborn = (Person) xstream.fromXML(temp);
            StaxDriverXstreamXML.tagEndTime(0);
            StaxDriverXstreamXML.nextMeasurment();
        }
        StaxDriverXstreamXML.saveMeasurmentsToDisk();
    }

      /*  private static Object fromString(String s) throws IOException, ClassNotFoundException {
            byte[] data = Base64.decode(s, Base64.DEFAULT);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object o = ois.readObject();
            ois.close();
            return o;
        }

        private static String toString(Serializable o) throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.close();
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        }*/
        /*
public void jsonParserAsBoon(Person p) {

    org.boon.json.ObjectMapper boonMapper = JsonFactory.create();

    for (int i = 0; i < forceHotSpot; i++) {
        boonTest.tagStartTime(0);
        String temp = boonMapper.writeValueAsString(p);
        Person personReborn = (Person) boonMapper.fromJson(temp, Person.class);

        boonTest.tagEndTime(0);
        boonTest.nextMeasurment();
    }
    boonTest.saveMeasurmentsToDisk();
}*/

}
