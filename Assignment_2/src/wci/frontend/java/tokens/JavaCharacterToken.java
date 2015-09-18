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
    protected void extract()
        throws Exception
    {
        StringBuilder textBuffer = new StringBuilder();
        StringBuilder valueBuffer = new StringBuilder();

        char currentChar = nextChar();  // consume initial quote
        textBuffer.append('\'');

        // if it is an escape character
        if (peekChar() == '\\') {
            currentChar = nextChar();
            textBuffer.append(currentChar);
           
            currentChar = nextChar();
            if (currentChar == '\\' || currentChar == '\'' || currentChar == 'n' ||
            		currentChar == 't')
            {
            	textBuffer.append(currentChar);
            }
            
            if (type != ERROR) 
            {
            	currentChar = nextChar();
            	if (currentChar == '\'') {
            		textBuffer.append(currentChar);
            		nextChar();
            		type = CHARACTER;
            		value = textBuffer.toString();
            	}
            	else {
            		type = ERROR;
            		value = UNEXPECTED_EOF;
            	}
            }
            
            else {
            	type = ERROR;
            	value = INVALID_CHARACTER;
            }
            
            
        }
        // else it is just a normal character, do this
        else {
            type = ERROR;
            value = UNEXPECTED_EOF;
        }

        text = textBuffer.toString();
    }
}
