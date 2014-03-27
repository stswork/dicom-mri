package utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Sagar Gopale on 3/9/14.
 */
public class FileExtensionCheckUtil {

    public static boolean isValidFile(String fileName) {

        boolean _isValid = true;
        String[] _arr = fileName.split("\\.");
        int _arrLen = _arr.length;
        int _lastIndex = _arrLen - 1;
        String _extension = _arr[_lastIndex];
        if(!StringUtils.equalsIgnoreCase(_extension, "dcm") && !StringUtils.equalsIgnoreCase(_extension, "jpg") && !StringUtils.equalsIgnoreCase(_extension, "jpeg")) {
            _isValid = false;
        }
        return _isValid;
    }

    public static String getFileExtension(String fileName) {
        String[] _arr = fileName.split("\\.");
        int _arrLen = _arr.length;
        int _lastIndex = _arrLen - 1;
        return  _arr[_lastIndex];
    }
}
