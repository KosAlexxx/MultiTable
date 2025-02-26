package org;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public enum NumberType{
        INT,
        DOUBLE,
        FLOAT,
        BYTE,
        LONG
    }

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.error("The config.properties file was not found on the classpath!");
                return;
            }
            properties.load(input);
            logger.info("Load file 'config.properties' is successful");

        } catch (IOException e) {
            logger.error("Error loading 'config.properties' file: ", e);
            return;
        }
        String stringIncrement = String.valueOf(properties.getProperty("increment"));
        NumberType numberType = (args.length > 0) ? NumberType.valueOf(args[0].toUpperCase()) : NumberType.INT; // default value

        switch (numberType){
            case INT:
                processInt(properties, stringIncrement);
                break;
            case DOUBLE:
                processDouble(properties, stringIncrement);
                break;
            case FLOAT:
                processFloat(properties, stringIncrement);
                break;
            case LONG:
                processLong(properties, stringIncrement);
                break;
            case BYTE:
                processByte(properties, stringIncrement);
                break;
            default:
                logger.error("Number type is not supported: {}", numberType);
                break;
        }

    }

    private static void processByte(Properties properties, String stringIncrement) {
        try {
            byte byteIncrement = Byte.parseByte(properties.getProperty("increment"));
            byte min = Byte.parseByte(properties.getProperty("min"));
            byte max = Byte.parseByte((properties.getProperty("max")));
            if (validateValues(min, max, byteIncrement)) {
                setLog(min, max, stringIncrement, String.valueOf(NumberType.BYTE));
                getMultitask(min, max, byteIncrement);
            }
        } catch(NumberFormatException e){
            logger.error("NumberFormatException: incorrect format double");
        }
    }

    private static void processLong(Properties properties, String stringIncrement) {
        try {
            long longIncrement = Long.parseLong(properties.getProperty("increment"));
            long min = Long.parseLong(properties.getProperty("min"));
            long max = Long.parseLong((properties.getProperty("max")));
            if (validateValues(min, max, longIncrement)) {
                setLog(min, max, stringIncrement, String.valueOf(NumberType.LONG));
                getMultitask(min, max, longIncrement);
            }
        } catch(NumberFormatException e){
            logger.error("NumberFormatException: incorrect format long");
        }
    }

    private static void processFloat(Properties properties, String stringIncrement) {
        try {
            float floatIncrement = Float.parseFloat(properties.getProperty("increment"));
            float min = Float.parseFloat(properties.getProperty("min"));
            float max = Float.parseFloat((properties.getProperty("max")));
            if (validateValues(min, max, floatIncrement)) {
                setLog(min, max, stringIncrement, String.valueOf(NumberType.FLOAT));
                getMultitask(min, max, floatIncrement);
            }
        } catch(NumberFormatException e){
            logger.error("NumberFormatException: incorrect format float");
        }
    }

    private static void processDouble(Properties properties, String stringIncrement) {
        try {
            double doubleIncrement = Double.parseDouble(properties.getProperty("increment"));
            double min = Double.parseDouble(properties.getProperty("min"));
            double max = Integer.parseInt((properties.getProperty("max")));
            if (validateValues(min, max, doubleIncrement)) {
                setLog(min, max, stringIncrement, String.valueOf(NumberType.DOUBLE));
                getMultitask(min, max, doubleIncrement);
            }
        } catch(NumberFormatException e){
            logger.error("NumberFormatException: incorrect format double");
        }
    }

    private static void processInt(Properties properties, String stringIncrement) {
        try {
            int intIncrement = Integer.parseInt(properties.getProperty("increment"));
            int min = Integer.parseInt(properties.getProperty("min"));
            int max = Integer.parseInt((properties.getProperty("max")));
            if (validateValues(min, max, intIncrement)) {
                setLog(min, max, stringIncrement, String.valueOf(NumberType.INT));
                getMultitask(min, max, intIncrement);
            }
        } catch(NumberFormatException e){
            logger.error("NumberFormatException: incorrect format int");
        }
    }

    /**
     * Method for view multitable on screen
     * @param min parametrized minimum value
     * @param max parametrized maximum value
     * @param increment step for multiplication
     * @param <T> int, double, long, byte, float
     */
    private static <T extends Number> void getMultitask(T min, T max, T increment) {
        // Convert values to double for universatility
        double minVal = min.doubleValue();
        double maxVal = max.doubleValue();
        double incrementVal = increment.doubleValue();

        // check the valid increment
        if (incrementVal <= 0) {
            throw new IllegalArgumentException("Increment must be a positive number");
        }

        int steps = (int) ((maxVal - minVal) / incrementVal);
        // Loop with double value
        for (int step = 0; step <= steps; step++) {
            double i = minVal + step * incrementVal;
            for (int innerStep = 0; innerStep <= steps; innerStep++) {
                double j = minVal + innerStep * incrementVal;
                System.out.printf("%10.6f", i * j); // Formatting for value with floating point
            }
            System.out.println();
        }
    }

    private static void setLog(double min, double max, String increment, String numberType) {
        logger.info("min: {}", min);
        logger.info("max: {}", max);
        logger.info("increment: {}", increment);
        logger.info("numberType: {}", numberType);
        logger.info("Run function getMultitask with parameters: min={}, max={}, increment={}", min, max, increment);
    }

    private static <T extends  Number> boolean validateValues(T min, T max, T increment) {
        if (min.doubleValue() > max.doubleValue()) {
            logger.error("Incorrect data entry: min ({}) cannot be greater than max ({})", min, max);
            return false;
        } else if (increment.doubleValue() <= 0) {
            logger.error("Increment cannot be equals to zero");
            return false;
        } else if (min.doubleValue() == 0 || max.doubleValue() == 0) {
            logger.error("Min or max cannot be equals to zero");
            return false;
        } else {
            return true;
        }
    }

}
