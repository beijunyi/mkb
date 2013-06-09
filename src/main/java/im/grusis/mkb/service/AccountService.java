package im.grusis.mkb.service;

import java.util.*;

import javax.annotation.PostConstruct;

import im.grusis.mkb.internal.MkbAccount;
import im.grusis.mkb.repository.AccountRepository;
import im.grusis.mkb.internal.accountFilter.AccountFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Mothership
 * Date: 13-5-31
 * Time: 上午12:01
 */
@Service
public class AccountService {
  @Autowired AccountRepository accountRepository;
  @Autowired ArchiveService archiveService;

  private Map<String, MkbAccount> macLookup = new LinkedHashMap<String, MkbAccount>();
  private Map<String, MkbAccount> usernameLookup = new LinkedHashMap<String, MkbAccount>();

  @PostConstruct
  public void init() {
    List<MkbAccount> accounts = accountRepository.readAll();
    for(MkbAccount account : accounts) {
      updateLookup(account);
    }
  }

  private void updateLookup(MkbAccount account) {
    String mac = account.getMac();
    if(mac != null) {
      macLookup.put(mac, account);
    }
    String username = account.getUsername();
    if(username != null) {
      usernameLookup.put(username, account);
      archiveService.addUsername(account.getUsername());
    }
  }

  private void deleteAccount(boolean backup, MkbAccount account) {
    macLookup.remove(account.getMac());
    usernameLookup.remove(account.getUsername());
    accountRepository.deleteAccount(account.getUsername(), backup);
  }

  public MkbAccount findAccountByMac(String mac) {
    return macLookup.get(mac);
  }

  public MkbAccount findAccountByUsername(String username) {
    return usernameLookup.get(username);
  }

  public void saveAccount(MkbAccount account) {
    updateLookup(account);
    accountRepository.createOrUpdateAccount(account);
  }

  public MkbAccount findFirst(AccountFilter filter) {
    Collection<MkbAccount> accounts = usernameLookup.values();
    for(MkbAccount account : accounts) {
     if(filter.accept(account)) {
       return account;
     }
    }
    return null;
  }

  public Collection<MkbAccount> findAll(AccountFilter filter) {
    Collection<MkbAccount> accounts = usernameLookup.values();
    Collection<MkbAccount> ret = new LinkedHashSet<MkbAccount>();
    for(MkbAccount account : accounts) {
      if(filter.accept(account)) {
        ret.add(account);
      }
    }
    return ret;
  }

  public void clear(boolean backup, Collection<MkbAccount> accounts) {
    for(MkbAccount account : accounts) {
      deleteAccount(backup, account);
    }
  }

  public void finishInvite(String invitor) {
    MkbAccount account = findAccountByUsername(invitor);
    account.setInviteCount(account.getInviteCount() + 1);
    saveAccount(account);
  }
}
