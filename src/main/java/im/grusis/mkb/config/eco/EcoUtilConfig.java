package im.grusis.mkb.config.eco;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import im.grusis.mkb.eco.util.dictionary.MkbDictionary;
import im.grusis.mkb.eco.util.password.MkbPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * User: Junyi BEI
 * Date: 18/01/2013
 * Time: 22:58
 */
@Configuration
@PropertySource("classpath:/eco_util.properties")
public class EcoUtilConfig {

  private static final String DICTIONARY_PREFIX = "dict.";
  private static final String PASSWORD_GENERATOR_PREFIX = "password";
  private static final String USERNAME = "username";
  private static final String NICKNAME = "nickname";
  private static final String CONSTRUCTOR_ARGUMENTS = ".args.";

  @Autowired Environment env;

  private <T> T getInstance(String prefix, Class<T> clazz) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    List<String> args = new ArrayList<String>();
    int i = 1;
    String arg;
    while((arg = env.getProperty(prefix + CONSTRUCTOR_ARGUMENTS + i++)) != null) {
      args.add(arg);
    }
    int length = args.size();
    Class<?>[] types = new Class[length];
    Arrays.fill(types, String.class);
    String[] argArray = new String[length];
    return clazz.getConstructor(types).newInstance(args.toArray(argArray));
  }

  private MkbDictionary getDictionary(String key) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    String prefix = env.getProperty(DICTIONARY_PREFIX + key);
    String className = env.getProperty(prefix);
    Class<? extends MkbDictionary> clazz = Class.forName(className).asSubclass(MkbDictionary.class);
    return getInstance(prefix, clazz);
  }

  @Bean(name = "username")
  public MkbDictionary getUsernameDictionary() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    return getDictionary(USERNAME);
  }

  @Bean(name = "nickname")
  public MkbDictionary getNicknameDictionary() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    return getDictionary(NICKNAME);
  }

  @Bean
  public MkbPasswordGenerator getPasswordGenerator() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException  {
    String prefix = env.getProperty(PASSWORD_GENERATOR_PREFIX);
    String className = env.getProperty(prefix);
    Class<? extends MkbPasswordGenerator> clazz = Class.forName(className).asSubclass(MkbPasswordGenerator.class);
    return getInstance(prefix, clazz);
  }

}
