package im.grusis.mkb.service;

import java.util.*;

import javax.annotation.PostConstruct;

import im.grusis.mkb.internal.StringMapSetArchive;
import im.grusis.mkb.internal.StringSetArchive;
import im.grusis.mkb.repository.ArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 下午11:53
 */
@Service
public class ArchiveService {

  private static final String UsernameArchiveName = "username";
  private static final String NicknameArchiveName = "nickname";

  @Autowired private ArchiveRepository archiveRepository;

  private StringSetArchive usernameArchive;
  private StringMapSetArchive nicknameArchive;

  @PostConstruct
  public void init() {
    usernameArchive = archiveRepository.getArchive(UsernameArchiveName, StringSetArchive.class);
    if(usernameArchive == null) {
      usernameArchive = new StringSetArchive(UsernameArchiveName, new LinkedHashSet<String>());
      archiveRepository.createOrUpdateArchive(usernameArchive);
    }
    nicknameArchive = archiveRepository.getArchive(NicknameArchiveName, StringMapSetArchive.class);
    if(nicknameArchive == null) {
      nicknameArchive = new StringMapSetArchive(NicknameArchiveName, new LinkedHashMap<String, Set<String>>());
      archiveRepository.createOrUpdateArchive(nicknameArchive);
    }
  }

  public void addUsername(String username) {
    if(usernameArchive.getSet().add(username)) {
      archiveRepository.createOrUpdateArchive(usernameArchive);
    }
  }

  public void addNickname(String server, String nickname) {
    Map<String, Set<String>> mapSet = nicknameArchive.getMapSet();
    Set<String> set = mapSet.get(server);
    if(set == null) {
      set = new LinkedHashSet<String>();
      mapSet.put(server, set);
    }
    if(set.add(nickname)) {
      archiveRepository.createOrUpdateArchive(nicknameArchive);
    }
  }

  public boolean existUsername(String username) {
    return usernameArchive.getSet().contains(username);
  }

  public boolean existNickname(String server, String username) {
    Set<String> set = nicknameArchive.getMapSet().get(server);
    return set != null && set.contains(username);
  }
}
