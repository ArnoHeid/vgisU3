import org.geotools.data.*;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Feature;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        String getCapabilities = "http://localhost:8080/geoserver/wfs?REQUEST=GetCapabilities&VERSION=1.1.0";

        Map<String, String> connectionParameters = new HashMap();
        connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", getCapabilities);

        DataStore data =null;
        try {
            data = DataStoreFinder.getDataStore(connectionParameters);

            String typeNames[] = data.getTypeNames();
            String typeName = typeNames[0];
            SimpleFeatureType schema = data.getSchema(typeName);

            System.out.println("SimpleFeatureType # Attributes = " + schema.getAttributeCount());

            FeatureSource<SimpleFeatureType, SimpleFeature> source = data.getFeatureSource(typeName);
            FeatureCollection<SimpleFeatureType, SimpleFeature> features = source.getFeatures();

            try (FeatureIterator<SimpleFeature> iterator = features.features()) {
                while (iterator.hasNext()) {
                    Feature feature = iterator.next();
                    System.out.println(feature.toString());
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(data != null)
                data.dispose();
        }


    }
}
