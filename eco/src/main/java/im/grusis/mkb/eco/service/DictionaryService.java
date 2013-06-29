package im.grusis.mkb.eco.service;

import im.grusis.mkb.eco.repository.DictionaryRepository;
import im.grusis.mkb.eco.util.dictionary.BasicDictRecord;
import im.grusis.mkb.eco.util.dictionary.BasicNicknameDict;
import im.grusis.mkb.eco.util.dictionary.BasicUsernameDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Mothership
 * Date: 13-6-29
 * Time: 下午5:58
 */
@Service
public class DictionaryService {

  @Autowired DictionaryRepository dictionaryRepository;

  public BasicUsernameDict newBasicUsernameDict(String prefix) {
    BasicUsernameDict basicUsernameDict = new BasicUsernameDict(prefix);
    BasicDictRecord record = dictionaryRepository.getDictRecord(BasicUsernameDict.class.getSimpleName(), BasicDictRecord.class);
    if(record != null) {
      record.apply(basicUsernameDict);
    }
    basicUsernameDict.init();
    return basicUsernameDict;
  }

  public BasicNicknameDict newBasicNicknameDict(String prefix) {
    BasicNicknameDict basicNicknameDict = new BasicNicknameDict(prefix);
    BasicDictRecord record = dictionaryRepository.getDictRecord(BasicNicknameDict.class.getSimpleName(), BasicDictRecord.class);
    if(record != null) {
      record.apply(basicNicknameDict);
    }
    basicNicknameDict.init();
    return basicNicknameDict;
  }

  public void updateBasicUsernameDictRecord(BasicUsernameDict basicUsernameDict) {
    String index = BasicUsernameDict.class.getSimpleName();
    BasicDictRecord record = dictionaryRepository.getDictRecord(index, BasicDictRecord.class);
    if(record == null) {
      record = new BasicDictRecord();
    }
    record.update(basicUsernameDict);
    dictionaryRepository.createOrUpdateRecord(index, record);
  }

  public void updateBasicNicknameDictRecord(BasicNicknameDict basicNicknameDict) {
    String index = BasicNicknameDict.class.getSimpleName();
    BasicDictRecord record = dictionaryRepository.getDictRecord(index, BasicDictRecord.class);
    if(record == null) {
      record = new BasicDictRecord();
    }
    record.update(basicNicknameDict);
    dictionaryRepository.createOrUpdateRecord(index, record);
  }

}
