package self.heresay.config;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ejb.access.LocalStatelessSessionProxyFactoryBean;
import self.heresay.BankTxFacadeLocal;

@Configuration
public class EJBConfig {

    @Bean
    public BankTxFacadeLocal bankTxFacade() throws NamingException {
    	  LocalStatelessSessionProxyFactoryBean factory = new LocalStatelessSessionProxyFactoryBean();
          Properties jndiProperties = new Properties();
		  jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.remote.client.InitialContextFactory"); 
		  factory.setJndiEnvironment(jndiProperties);
		  factory.setJndiName("java:global/deadlyDuos/service-yetagain-1.0/BankTxFacade!self.heresay.BankTxFacadeLocal");
		  factory.setBusinessInterface(self.heresay.BankTxFacadeLocal.class);
		  factory.afterPropertiesSet();
		  BankTxFacadeLocal ejb = (BankTxFacadeLocal) factory.getObject();
		  return ejb;
    }
}