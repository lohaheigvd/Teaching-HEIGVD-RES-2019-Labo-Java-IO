package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    String[] arrOfStr = new String[2];
    int i = 0;

    if(lines.contains("\r") || lines.contains("\n")) {
      while(lines.charAt(i) != '\r' && lines.charAt(i) != '\n'){i++;}
      i++;
      if(lines.length() >= i+1) {
        if (lines.substring(i-1, i + 1).contains("\r\n")) ++i;
      }
      arrOfStr[0] = lines.substring(0, i);
      arrOfStr[1] = lines.substring(i, lines.length());
    }
    else{
      arrOfStr[0] = "";
      arrOfStr[1] = lines;
    }
    return arrOfStr;
  }
}
