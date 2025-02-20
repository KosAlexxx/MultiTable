package org;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
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
        String numberType = (args.length > 0) ? args[0].toLowerCase() : "int";

            switch (numberType){
                case "double":
                    try {
                        double doubleIncrement = Double.parseDouble(properties.getProperty("increment"));
                        double minDouble = Double.parseDouble(properties.getProperty("min"));
                        double maxDouble = Double.parseDouble(properties.getProperty("max"));
                        if (validateValues(minDouble, maxDouble, doubleIncrement)) {
                            setLog(minDouble, maxDouble, stringIncrement, numberType);
                            getMultitask(minDouble, maxDouble, doubleIncrement);
                        }
                    } catch (NumberFormatException e){
                        logger.error("NumberFormatException: incorrect format double");
                }
                    break;
                case "long":
                    try {
                        long longIncrement = Long.parseLong(properties.getProperty("increment"));
                        long minLong = Long.parseLong(properties.getProperty("min"));
                        long maxLong = Long.parseLong(properties.getProperty("max"));
                        if (validateValues(minLong, maxLong, longIncrement)) {
                            setLog(minLong, maxLong, stringIncrement, numberType);
                            getMultitask(minLong, maxLong, longIncrement);
                        }
                    } catch (NumberFormatException e){
                        logger.error("NumberFormatException: incorrect format long");
                    }
                    break;
                case "byte":
                    try {
                        byte byteIncrement = Byte.parseByte(properties.getProperty("increment"));
                        byte minByte = Byte.parseByte(properties.getProperty("min"));
                        byte maxByte = Byte.parseByte(properties.getProperty("max"));
                        if (validateValues(minByte, maxByte, byteIncrement)) {
                            setLog(minByte, maxByte, stringIncrement, numberType);
                            getMultitask(minByte, maxByte, byteIncrement);
                        }
                    } catch (NumberFormatException e) {
                       logger.error("NumberFormatException: incorrect format byte");
                }
                    break;
                case "int":
                    try {
                        int intIncrement = Integer.parseInt(properties.getProperty("increment"));
                        int min = Integer.parseInt(properties.getProperty("min"));
                        int max = Integer.parseInt((properties.getProperty("max")));
                        if (validateValues(min, max, intIncrement)) {
                            setLog(min, max, stringIncrement, numberType);
                            getMultitask(min, max, intIncrement);
                        }
                    } catch(NumberFormatException e){
                        logger.error("NumberFormatException: incorrect format int");
                    }
                    break;
                case "float":
                    try {
                        float floatIncrement = Float.parseFloat(properties.getProperty("increment"));
                        float floatMin = Float.parseFloat(properties.getProperty("min"));
                        float floatMax = Float.parseFloat((properties.getProperty("max")));
                        if (validateValues(floatMin, floatMax, floatIncrement)) {
                            setLog(floatMin, floatMax, stringIncrement, numberType);
                            getMultitask(floatMin, floatMax, floatIncrement);
                        }
                    } catch (NumberFormatException e){
                        logger.error("NumberFormatException: incorrect format float");
                    }
                    break;
                default:
                    logger.error("Number type is not supported: {}", numberType);
                    break;
            }

    }

    private static <T extends Number> void getMultitask(T min, T max, T increment) {
        // Преобразуем значения к double для универсальности
        double minVal = min.doubleValue();
        double maxVal = max.doubleValue();
        double incrementVal = increment.doubleValue();

        // Проверка на валидность increment
        if (incrementVal <= 0) {
            throw new IllegalArgumentException("Increment must be a positive number");
        }

        int steps = (int) ((maxVal - minVal) / incrementVal);
        // Цикл с использованием double
        for (int step = 0; step <= steps; step++) {
            double i = minVal + step * incrementVal;
            for (int innerStep = 0; innerStep <= steps; innerStep++) {
                double j = minVal + innerStep * incrementVal;
                System.out.printf("%10.6f", i * j); // Форматирование для чисел с плавающей точкой
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
