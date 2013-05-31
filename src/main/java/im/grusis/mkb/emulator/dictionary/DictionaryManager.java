package im.grusis.mkb.emulator.dictionary;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * User: beij
 * Date: 31/05/13
 * Time: 10:38
 */
@Component
@PropertySource("classpath:/dict.properties")
public class DictionaryManager {

  private static final Logger Log = LoggerFactory.getLogger(DictionaryManager.class);

  @Autowired
  Environment env;

  private Map<String, MkbDictionary> dictMap = new HashMap<String, MkbDictionary>();

  public DictionaryManager() {
    Reflections reflections = new Reflections("im.grusis.mkb.emulator.dictionary.dicts");
    Set<Class<? extends MkbDictionary>> classes = reflections.getSubTypesOf(MkbDictionary.class);
    for(Class<? extends MkbDictionary> clazz : classes) {
      try {
        Constructor<? extends MkbDictionary> constructor = clazz.getConstructor();
        MkbDictionary mkbDictionary = constructor.newInstance();
        String keys = env.getProperty(clazz.getSimpleName());
        if(keys != null && !(keys = keys.trim().replace(" ", "")).isEmpty()) {
          String[] keyArray = keys.split(",");
          for(String key : keyArray) {
            String value = env.getProperty(clazz.getSimpleName() + '.' + key);
            Field field = clazz.getField(key);
            Class<?> fieldClass = field.getType();
            Method valueOf = fieldClass.getMethod("valueOf", String.class);
            Object valueObj = valueOf.invoke(null, value);
            field.set(mkbDictionary, valueObj);
          }
        }
        dictMap.put(clazz.getName(), mkbDictionary);
        dictMap.put(clazz.getSimpleName(), mkbDictionary);
        dictMap.put(mkbDictionary.getAlias(), mkbDictionary);
      } catch(Exception e) {
        Log.error("Cannot initiate dictionary {}", clazz.getName());
      }
    }
  }

  public MkbDictionary getDict(String key) {
    return dictMap.get(key);
  }

  public Set<String> getUsedInstances(String clazzName) {
    return null;
  }
}
