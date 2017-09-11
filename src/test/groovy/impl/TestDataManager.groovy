package impl

import groovy.json.JsonSlurper
import groovy.json.internal.LazyMap

/**
 * Created by FOB Solutions
 */
class TestDataManager {

    private static ThreadLocal<LazyMap> testData = new ThreadLocal<>([:])
    private static final String DATA_FILE_NAME = "data.json"


    /**
     * Saves a key-value pair to map for given device
     * @param key Key for the value
     * @param value value to be saved
     */
    static void setTestData(String key, String value) {
        getTestData().put(key, value)
    }

    /**
     * Returns value from device specific map
     * @param key Key for the value
     * @return String value from map
     */
    static String getValue(String key) {
        getTestData().get(key)
    }

    private static LazyMap getTestData() {
        if (testData.get() == null) {
            testData.set([:] as LazyMap)
        }
        testData.get() as LazyMap
    }

    /**
     * Returns all test data from file
     * @return LazyMap of test data
     */
    static LazyMap getTestDataFileContent() {
        File dataFile = new File(DATA_FILE_NAME)
        if (!dataFile.exists()) throw new RIHATestException("Data file is missing from project root!")
        new JsonSlurper().parse(new File(DATA_FILE_NAME)) as LazyMap
    }

}
