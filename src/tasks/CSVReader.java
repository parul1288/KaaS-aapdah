package tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

	private BufferedReader br;
  private boolean hasNext = true;
  private char separator;
  private char quotechar;
  private int skipLines;
  private boolean linesSkiped;

  public static final char DEFAULT_SEPARATOR = ',';
  public static final char DEFAULT_QUOTE_CHARACTER = '"';
  public static final int DEFAULT_SKIP_LINES = 0;

  public CSVReader(Reader reader) {
    this(reader, DEFAULT_SEPARATOR);
  }
  public CSVReader(Reader reader, char separator) {
    this(reader, separator, DEFAULT_QUOTE_CHARACTER);
}
  public CSVReader(Reader reader, char separator, char quotechar) {
    this(reader, separator, quotechar, DEFAULT_SKIP_LINES);
}
  public CSVReader(Reader reader, char separator, char quotechar, int line) {
    this.br = new BufferedReader(reader);
    this.separator = separator;
    this.quotechar = quotechar;
    this.skipLines = line;
}
  public List<String[]> readAll() throws IOException {

    List<String[]> allElements = new ArrayList<String[]>();
    while (hasNext) {
        String[] nextLineAsTokens = readNext();
        if (nextLineAsTokens != null)
            allElements.add(nextLineAsTokens);
    }
    return allElements;

}
  public String[] readNext() throws IOException {

    String nextLine = getNextLine();
    return hasNext ? parseLine(nextLine) : null;
}
  private String getNextLine() throws IOException {
    if (!this.linesSkiped) {
        for (int i = 0; i < skipLines; i++) {
            br.readLine();
        }
        this.linesSkiped = true;
    }
    String nextLine = br.readLine();
    if (nextLine == null) {
        hasNext = false;
    }
    return hasNext ? nextLine : null;
}
  public String[] parseLine(String nextLine) throws IOException {

    if (nextLine == null) {
        return null;
    }

    List<String> tokensOnThisLine = new ArrayList<String>();
    StringBuffer sb = new StringBuffer();
    boolean inQuotes = false;
    do {
            if (inQuotes) {
            // continuing a quoted section, reappend newline
            sb.append("\n");
            nextLine = getNextLine();
            if (nextLine == null)
                break;
        }
        for (int i = 0; i < nextLine.length(); i++) {

            char c = nextLine.charAt(i);
            if (c == quotechar) {
                    // this gets complex... the quote may end a quoted block, or escape another quote.
                    // do a 1-char lookahead:
                    if( inQuotes  // we are in quotes, therefore there can be escaped quotes in here.
                        && nextLine.length() > (i+1)  // there is indeed another character to check.
                        && nextLine.charAt(i+1) == quotechar ){ // ..and that char. is a quote also.
                            // we have two quote chars in a row == one quote char, so consume them both and
                            // put one on the token. we do *not* exit the quoted text.
                            sb.append(nextLine.charAt(i+1));
                            i++;
                    }else{
                            inQuotes = !inQuotes;
                            // the tricky case of an embedded quote in the middle: a,bc"d"ef,g
                            if(i>2 //not on the begining of the line
                                            && nextLine.charAt(i-1) != this.separator //not at the begining of an escape sequence 
                                            && nextLine.length()>(i+1) &&
                                            nextLine.charAt(i+1) != this.separator //not at the     end of an escape sequence
                            ){
                                    sb.append(c);
                            }
                    }
            } else if (c == separator && !inQuotes) {
                tokensOnThisLine.add(sb.toString());
                sb = new StringBuffer(); // start work on next token
            } else {
                sb.append(c);
            }
        }
    } while (inQuotes);
    tokensOnThisLine.add(sb.toString());
    return (String[]) tokensOnThisLine.toArray(new String[0]);

}
  public void close() throws IOException{
    br.close();
}

  
}

