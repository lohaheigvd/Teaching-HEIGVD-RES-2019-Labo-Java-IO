package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 */

public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());


  private boolean begin = true;
  private boolean wasEscape = false;
  private int compteur = 0;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String[] array = new String[]{new String("EMPTY"), new String("EMPTY")};
    String temp = new String();
    array[1] = str.substring(off, off+len);
    int i = 0;

    if(begin){
      begin = false;
      temp += ++compteur + "\t";
    }

    if(!array[1].contains("\n") && !array[1].contains("\r")){
      temp += array[1];
    }

    while(!array[0].equals("")) {
      array = Utils.getNextLine(array[1]);
      if(array[0].equals("") && i > 0){
        temp += array[1];
      }
      temp += array[0];
      wasEscape = temp.equals("\r") ? true : false;
      if ((array[0].contains("\n") || array[0].contains("\r")) && !wasEscape) {
          temp += ++compteur + "\t";
      }
      i++;
    }
      super.write(temp, 0, temp.length());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(new String(cbuf),  off,  len);
  }

  @Override
  public void write(int c) throws IOException {
    char alpha = (char)c;
    write("" + alpha);
  }

}
