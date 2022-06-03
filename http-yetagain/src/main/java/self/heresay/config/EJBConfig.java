package self.heresay.config;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import self.heresay.BankTxFacadeRemote;

@Configuration
@PropertySource("classpath:applicatiion.properties")
public class EJBConfig {

    @Bean
    public BankTxFacadeRemote bankTxFacade() throws NamingException {
    	Properties jndiProperties = new Properties();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        Context ctx = new InitialContext(jndiProperties);
        Object remote = ctx.lookup("ejb:deadlyDuos/http-yetagain-1.0/BankTxFacade!self.heresay.BankTxFacadeRemote");
        BankTxFacadeRemote ejb = (BankTxFacadeRemote) PortableRemoteObject.narrow(remote,BankTxFacadeRemote.class);
		return ejb;
    }
}