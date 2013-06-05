package im.grusis.mkb.emulator.dictionary;

import im.grusis.mkb.emulator.dictionary.dicts.BasicNicknameDict;
import im.grusis.mkb.emulator.dictionary.dicts.BasicUsernameDict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
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


  public MkbDictionary getUsernameDict(String prefix) {
    return new BasicUsernameDict(prefix);
  }

  public MkbDictionary getNicknameDict(String prefix) {
    return new BasicNicknameDict(prefix);
  }

}
