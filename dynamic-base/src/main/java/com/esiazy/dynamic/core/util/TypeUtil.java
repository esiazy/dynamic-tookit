package com.esiazy.dynamic.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wxf
 * @date 2021/5/28 10:00
 */
public class TypeUtil {
    private static final Pattern NUMBER_WITH_TRAILING_ZEROS_PATTERN = Pattern.compile("\\.0*$");

    public static int intValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }

        int scale = decimal.scale();
        if (scale >= -100 && scale <= 100) {
            return decimal.intValue();
        }

        return decimal.intValueExact();
    }

    public static Date castToDate(Object value) {
        return castToDate(value, null);
    }

    public static Date castToDate(Object value, String format) {
        if (value == null) {
            return null;
        }

        if (value instanceof Date) { // 使用频率最高的，应优先处理
            return (Date) value;
        }

        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }

        long longValue = -1;

        if (value instanceof BigDecimal) {
            longValue = longValue((BigDecimal) value);
            return new Date(longValue);
        }

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
            return new Date(longValue);
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.indexOf('-') > 0 || strVal.indexOf('+') > 0) {
                if (format == null) {
                    if (strVal.length() == 10) {
                        format = "yyyy-MM-dd";
                    } else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                        format = "yyyy-MM-dd HH:mm:ss";
                    } else if (strVal.length() == 29
                            && strVal.charAt(26) == ':'
                            && strVal.charAt(28) == '0') {
                        format = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
                    } else {
                        format = "yyyy-MM-dd HH:mm:ss";
                    }
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                try {
                    return dateFormat.parse(strVal);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("can not cast to Date, value : " + strVal);
                }
            }
            if (strVal.length() == 0) {
                return null;
            }
            longValue = Long.parseLong(strVal);
        }

        return new Date(longValue);
    }

    public static Character castToChar(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Character) {
            return (Character) value;
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            if (strVal.length() != 1) {
                throw new IllegalArgumentException("can not cast to char, value : " + value);
            }
            return strVal.charAt(0);
        }
        throw new IllegalArgumentException("can not cast to char, value : " + value);
    }

    public static long longValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }

        int scale = decimal.scale();
        if (scale >= -100 && scale <= 100) {
            return decimal.longValue();
        }

        return decimal.longValueExact();
    }

    public static Integer castToInt(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Integer) {
            return (Integer) value;
        }

        if (value instanceof BigDecimal) {
            return intValue((BigDecimal) value);
        }

        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equalsIgnoreCase(strVal)) {
                return null;
            }
            if (strVal.indexOf(',') != -1) {
                strVal = strVal.replaceAll(",", "");
            }

            Matcher matcher = NUMBER_WITH_TRAILING_ZEROS_PATTERN.matcher(strVal);
            if (matcher.find()) {
                strVal = matcher.replaceAll("");
            }
            return Integer.parseInt(strVal);
        }

        if (value instanceof Boolean) {
            return (Boolean) value ? 1 : 0;
        }
        throw new IllegalArgumentException("can not cast to int, value : " + value);
    }

    public static Float castToFloat(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)) {
                return null;
            }
            if (strVal.indexOf(',') != 0) {
                strVal = strVal.replaceAll(",", "");
            }
            return Float.parseFloat(strVal);
        }
        throw new IllegalArgumentException("can not cast to float, value : " + value);
    }

    public static short shortValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        }

        int scale = decimal.scale();
        if (scale >= -100 && scale <= 100) {
            return decimal.shortValue();
        }

        return decimal.shortValueExact();
    }

    public static Boolean castToBoolean(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        if (value instanceof BigDecimal) {
            return intValue((BigDecimal) value) == 1;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue() == 1;
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 //
                    || "null".equals(strVal) //
                    || "NULL".equals(strVal)) {
                return null;
            }
            if ("true".equalsIgnoreCase(strVal) //
                    || "1".equals(strVal)) {
                return Boolean.TRUE;
            }
            if ("false".equalsIgnoreCase(strVal) //
                    || "0".equals(strVal)) {
                return Boolean.FALSE;
            }
            if ("Y".equalsIgnoreCase(strVal) //
                    || "T".equals(strVal)) {
                return Boolean.TRUE;
            }
            if ("F".equalsIgnoreCase(strVal) //
                    || "N".equals(strVal)) {
                return Boolean.FALSE;
            }
        }
        throw new IllegalArgumentException("can not cast to boolean, value : " + value);
    }

    public static Short castToShort(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal) {
            return shortValue((BigDecimal) value);
        }

        if (value instanceof Number) {
            return ((Number) value).shortValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0
                    || "null".equals(strVal)
                    || "NULL".equals(strVal)) {
                return null;
            }
            return Short.parseShort(strVal);
        }

        throw new IllegalArgumentException("can not cast to short, value : " + value);
    }


    public static BigInteger castToBigInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        if (value instanceof Float || value instanceof Double) {
            return BigInteger.valueOf(((Number) value).longValue());
        }
        if (value instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) value;
            int scale = decimal.scale();
            if (scale > -1000 && scale < 1000) {
                return ((BigDecimal) value).toBigInteger();
            }
        }
        String strVal = value.toString();
        if (strVal.length() == 0
                || "null".equals(strVal)
                || "NULL".equals(strVal)) {
            return null;
        }
        return new BigInteger(strVal);
    }

    public static BigDecimal castToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Float) {
            if (Float.isNaN((Float) value) || Float.isInfinite((Float) value)) {
                return null;
            }
        } else if (value instanceof Double) {
            if (Double.isNaN((Double) value) || Double.isInfinite((Double) value)) {
                return null;
            }
        } else if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        } else if (value instanceof Map && ((Map<?, ?>) value).size() == 0) {
            return null;
        }

        String strVal = value.toString();

        if (strVal.length() == 0 || "null".equalsIgnoreCase(strVal)) {
            return null;
        }

        if (strVal.length() > 65535) {
            throw new IllegalArgumentException("decimal overflow");
        }
        return new BigDecimal(strVal);
    }

    public static Long castToLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return longValue((BigDecimal) value);
        }

        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equalsIgnoreCase(strVal)) {
                return null;
            }
            if (strVal.indexOf(',') != -1) {
                strVal = strVal.replaceAll(",", "");
            }
            try {
                return Long.parseLong(strVal);
            } catch (NumberFormatException ex) {
                //
            }
        }
        if (value instanceof Boolean) {
            return (Boolean) value ? 1L : 0L;
        }
        throw new IllegalArgumentException("can not cast to long, value : " + value);
    }

    public static Double castToDouble(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0 || "null".equalsIgnoreCase(strVal)) {
                return null;
            }
            if (strVal.indexOf(',') != -1) {
                strVal = strVal.replaceAll(",", "");
            }
            return Double.parseDouble(strVal);
        }

        if (value instanceof Boolean) {
            return (Boolean) value ? 1D : 0D;
        }

        throw new IllegalArgumentException("can not cast to double, value : " + value);
    }
}
