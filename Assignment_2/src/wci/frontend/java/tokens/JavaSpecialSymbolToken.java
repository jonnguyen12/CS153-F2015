package wci.frontend.java.tokens;
import wci.frontend.* ;
import wci.frontend.java.JavaToken;
import static wci.frontend.java.JavaErrorCode.*;
import static wci.frontend.java.JavaTokenType.*;
import static wci.frontend.pascal.PascalErrorCode.INVALID_CHARACTER;
import static wci.frontend.pascal.PascalTokenType.ERROR;
import static wci.frontend.pascal.PascalTokenType.SPECIAL_SYMBOLS;

/**
 * Created by phucnguyen on 9/16/15.
 */
public class JavaSpecialSymbolToken extends JavaToken {
	/**
	 * Constructor
	 */
	public JavaSpecialSymbolToken(Source source) throws Exception
	{
		super(source);
	}
	/**
	 * Extract a Java special symbol
	 */
	
	protected void extract() throws Exception
    {
        char currentChar = currentChar();

        text = Character.toString(currentChar);
        type = null;

        switch (currentChar) {

            // Single character special symbols.
            case '@':  case ',': case ':':  case '\'': case '(':
            case ')': case '{':  case '}':  case '[':  case ']':
            case '?':  case '~':  case ';': case '.': case '"':
            {
                nextChar();  // consume character
                break;
            }

            // - or -- or -=
            case '-': {
                currentChar = nextChar(); // consume '-'

                if (currentChar == '-') {
                    text += currentChar;
                    nextChar(); // consume another '-'
                }
                else if (currentChar == '=') {
                    text += currentChar;
                    nextChar(); // consume '='

                }
                break;
            }
           
            

            // + or ++ or +=
            case '+': {
                currentChar = nextChar(); // consume '+'

                if (currentChar == '+') {
                    text += currentChar;
                    nextChar(); // consume another '+'
                }
                else if (currentChar == '=') {
                    text += currentChar;
                    nextChar(); // consume '='

                }
                break;
            }
            
            // < or <= or << or <<=
            case '<': {
                currentChar = nextChar();  // consume '<';

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar();  // consume '='
                }
                else if (currentChar == '<') {
                    text += currentChar;
                    currentChar = nextChar();  // consume '<'
                    if (currentChar == '=') {
                        text += currentChar;
                        nextChar(); // consume '='
                    }
                }

                break;
            }


            

            // * or *= or */
            case '*': {
                currentChar = nextChar(); // consume '*'

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar(); // consume '='
                }
                else if (currentChar == '/') {
                    text += currentChar;
                    nextChar(); // consume '/'
                }
                break;
            }


            

            // | or |= or ||
            case '|': {
                currentChar = nextChar(); // consume '|'

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar(); // consume '='
                }
                else if (currentChar == '|') {
                    text += currentChar;
                    nextChar(); // consume another '|'
                }
                break;
            }
            
         // > or >= or >> or >>=
            case '>': {
                currentChar = nextChar();  // consume '>'

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar();  // consume '='
                }
                else if (currentChar == '>') {
                    text += currentChar;
                    currentChar = nextChar(); // consume '>'
                    if (currentChar == '=') {
                        text += currentChar;
                        nextChar(); // consume '='
                    }
                }

                break;
            }

            // & or && or &=
            case '&': {
                currentChar = nextChar(); // consume '&'

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar(); // consume '='
                }
                else if (currentChar == '&') {
                    text += currentChar;
                    nextChar(); // consume another '&'
                }
                break;
            }

            // ! or !=
            case '!': {
                currentChar = nextChar(); // consume '!'

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar(); // consume '='
                }
                break;
            }
            
         // / or /= or /* or //
            case '/': {
                currentChar = nextChar(); // consume '/'

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar(); // consume '='
                }
                else if (currentChar == '*') {
                    text += currentChar;
                    nextChar(); // consume '*'
                }
                else if (currentChar == '/') {
                    text += currentChar;
                    nextChar(); // consume another '/'
                }
                break;
            }

            // ^ or ^=
            case '^': {
                currentChar = nextChar(); // consume '^'

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar(); // consume '='
                }
                break;
            }

            // % or %=
            case '%': {
                currentChar = nextChar(); // consume '%'

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar(); // consume '='
                }
                break;
            }

            // = or ==
            case '=': {
                currentChar = nextChar(); // consume '='

                if (currentChar == '=') {
                    text += currentChar;
                    nextChar(); // consume another '='
                }
                break;
            }

            default: {
                nextChar();  // consume bad character
                type = ERROR;
                value = INVALID_CHARACTER;
            }
        }

        if (type == null) {
            type = SPECIAL_SYMBOLS.get(text);
        }
    }
}
