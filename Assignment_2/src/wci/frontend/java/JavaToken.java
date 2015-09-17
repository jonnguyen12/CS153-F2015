package wci.frontend.java.tokens;

import wci.frontend.Source;
import wci.frontend.Token;

/**
 * Created by phucnguyen on 9/16/15.
 */
public class JavaToken extends Token {
    /**
     * Constructor.
     * @param source the source from where to fetch the token's characters.
     * @throws Exception if an error occurred.
     */
    protected JavaToken(Source source)
            throws Exception
    {
        super(source);
    }
}
