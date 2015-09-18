package wci.frontend.java.tokens;

import wci.frontend.Source;
import wci.frontend.java.JavaToken;
import static wci.frontend.Source.EOL;
import static wci.frontend.Source.EOF;
import static wci.frontend.java.JavaTokenType.*;
import static wci.frontend.java.JavaErrorCode.*;
/**
 * Created by phucnguyen on 9/16/15.
 */
public class JavaStringToken extends JavaToken
{
    /**
     * Constructor.
     * @param source the source from where to fetch the token's characters.
     * @throws Exception if an error occurred.
     */
    public JavaStringToken(Source source)
            throws Exception
    {
        super(source);
    }

    /**
     * Extract a Java string token from the source.
     * @throws Exception if an error occurred.
     */
    protected void extract() throws Exception
    {
        StringBuilder textBuffer = new StringBuilder();
        StringBuilder valueBuffer = new StringBuilder();

        char currentChar = nextChar();  // consume initial quote
        textBuffer.append('"');

        // Get string characters.
        do
        {
            // escape character
            if (currentChar == '\\')
            {
                currentChar = nextChar(); //consume '\' character

                if (currentChar == 'n') {
                    textBuffer.append("\\n");
                    valueBuffer.append("\n");
                }
                else if (currentChar == 't') {
                    textBuffer.append("\\t");
                    valueBuffer.append("\t");
                }
                else if (currentChar == '"') {
                    textBuffer.append("\\\"");
                    valueBuffer.append(currentChar);
                }
                else if (currentChar == '\\' || currentChar == '\'') {
                    textBuffer.append(currentChar);
                    valueBuffer.append(currentChar);
                }
                else if (currentChar == '\n') {
                    valueBuffer.append(currentChar);
                    valueBuffer.deleteCharAt(valueBuffer.length() - 1);
                } else {
                    type = ERROR;
                    value = INVALID_CHARACTER;
                }

                currentChar = nextChar(); // consume the escape character, valid it or not
            } else if (currentChar == EOL)
            {
                type = ERROR;
                value = INVALID_STRING;
            } else if (type != ERROR && (currentChar != '"') && (currentChar != EOF))
            {
                textBuffer.append(currentChar);
                valueBuffer.append(currentChar);
                currentChar = nextChar();  // consume character
            }
        } while (type != ERROR && (currentChar != '"') && (currentChar != EOF));

        if (type != ERROR)
        {
            if (currentChar == '"')
            {
                nextChar();  // consume final quote
                textBuffer.append('"');

                type = STRING;
                value = valueBuffer.toString();
            } else
            {
                type = ERROR;
                value = UNEXPECTED_EOF;
            }
        }else{
            currentChar = nextChar();
        }

        text = textBuffer.toString();
    }
}