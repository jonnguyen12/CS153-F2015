package wci.frontend.java.tokens;

import wci.frontend.*;
import wci.frontend.java.*;
import static wci.frontend.Source.EOF;
import static wci.frontend.Source.EOL;
import static wci.frontend.java.JavaTokenType.*;
import static wci.frontend.java.JavaErrorCode.*;

import wci.frontend.Source;

/**
 * Created by phucnguyen on 9/16/15.
 */
public class JavaCharacterToken extends JavaToken {
	 /**
     * Constructor.
     * @param source the source from where to fetch the token's characters.
     * @throws Exception if an error occurred.
     */
    public JavaCharacterToken(Source source)
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

        char currentChar = nextChar();  // consume initial quote
        textBuffer.append('\'');

        if (currentChar == '\\') {   // check for escape character
            currentChar = nextChar();  // consume '\' character
            textBuffer.append('\\');

            if (currentChar == '\\' || currentChar == '\'' || currentChar == 'n' || currentChar == 't') {
                textBuffer.append(currentChar);
            } else {
                type = ERROR;
                value = INVALID_CHARACTER;
            }


            if (type != ERROR) {
                currentChar = nextChar();

                if (currentChar == '\'') {
                    textBuffer.append('\'');
                    nextChar(); // consume final quote
                    type = CHARACTER;
                    value = textBuffer.toString();
                }
                else {
                    type = ERROR;
                    value = UNEXPECTED_EOF;
                }
            }
        }
        else { // if the character is normal, no escape
            textBuffer.append(currentChar);
            currentChar = nextChar(); // consume character

            if (currentChar == '\'') {
                nextChar();  // consume final quote
                textBuffer.append('\'');
                type = CHARACTER;
                value = textBuffer.toString();
            }
            else {
                type = ERROR;
                value = UNEXPECTED_EOF;
            }
        }

        text = textBuffer.toString();
    }
}
