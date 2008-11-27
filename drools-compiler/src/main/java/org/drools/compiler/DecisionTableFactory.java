package org.drools.compiler;

import java.io.InputStream;
import java.io.Reader;

import org.drools.ProviderInitializationException;
import org.drools.builder.DecisionTableConfiguration;

public class DecisionTableFactory {
    private static volatile DecisionTableProvider provider;
    
    public static void setDecisionTableProvider(DecisionTableProvider provider) {
        DecisionTableFactory.provider = provider;
    }
    
    public static String loadFromInputStream(InputStream is, DecisionTableConfiguration configuration) {
        if ( provider == null ) {
            loadProvider();
        }
        return provider.loadFromInputStream( is, configuration );
    } 

    public static String loadFromReader(Reader reader, DecisionTableConfiguration configuration) {
        if ( provider == null ) {
            loadProvider();
        }
        return provider.loadFromReader( reader, configuration );
    } 
    
    private static void loadProvider() {
        try {
            // we didn't find anything in properties so lets try and us reflection
            Class<DecisionTableProvider> cls = ( Class<DecisionTableProvider> ) Class.forName( "org.drools.decisiontable.DecisionTableProviderImpl" );
            setDecisionTableProvider( cls.newInstance() );
        } catch ( Exception e2 ) {
            throw new ProviderInitializationException( "Provider org.drools.decisiontable.DecisionTableProviderImpl could not be set." );
        }
    }       
}