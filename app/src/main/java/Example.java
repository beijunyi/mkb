import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * User: Mothership
 * Date: 13-7-3
 * Time: 下午10:26
 */
public class Example implements Runnable {

  private String baseUrl;
  private String filePath;

  public Example(String baseUrl, String filePath) {
    this.baseUrl = baseUrl;
    this.filePath = filePath;
  }

  @Override
  public void run() {
    try {
      LinkedHashSet<User> users = createUsersAcrossThread(baseUrl);
      save(users, filePath);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public User getUser(Element element) {
    String text = element.text();
    text = text.replace("<br>", "\n");
    text = text.replace(":", "：");
    text = text.replace("账", "帐");
    text = text.replace("ID", "名");
    String baiduId;
    String role = null;
    String username = null;
    String server = null;
    Matcher bim = Pattern.compile("[\\w\\W]*百度[^：]*：?\\s*([^\\s]+)[\\w\\W]*").matcher(text);
    if(bim.matches()) {
      baiduId = bim.group(1);
    } else {
      System.out.println("Discard {" + text + "} due to invalid baiduId");
      return null;
    }
    Matcher rm = Pattern.compile("[\\w\\W]*游戏名：?\\s*([^\\s]+)[\\w\\W]*").matcher(text);
    if(rm.matches()) {
      role = rm.group(1);
    } else {
      System.out.println("Discard {" + text + "} due to invalid role");
      return null;
    }
    Matcher um = Pattern.compile("[\\w\\W]*帐号：?\\s*([^\\s]+)[\\w\\W]*").matcher(text);
    if(um.matches()) {
      username = um.group(1);
    } else {
      System.out.println("Discard {" + text + "} due to invalid username");
      return null;
    }
    Matcher sm = Pattern.compile("[\\w\\W]*服务器：?\\s*([^\\s]+)[\\w\\W]*").matcher(text);
    if(sm.matches()) {
      server = sm.group(1);
    } else {
      System.out.println("Discard {" + text + "} due to invalid server");
      return null;
    }
    System.out.println("Found user " + baiduId + "\t" + role + "\t" + username + "\t" + server);
    return new User(baiduId, role, username, server);
  }

  public int findLastPage(Document doc) {
    Elements elements = doc.select(".l_pager a");
    for(Element element : elements) {
      String href = StringUtils.trimToNull(element.attr("href"));
      String text = StringUtils.trimToNull(element.text());
      if(href == null || text == null) continue;
      if(text.equals("尾页")) {
        Pattern pattern = Pattern.compile(".*pn=(\\d+)");
        Matcher matcher = pattern.matcher(href);
        if(matcher.matches()) {
          return Integer.parseInt(matcher.group(1));
        }
      }
    }
    return 1;
  }

  public List<String> pageLinks(String url, int last) {
    List<String> ret = new ArrayList<String>();
    for(int i = 1; i <= last; i++) {
      ret.add(url + "?pn=" + i);
    }
    return ret;
  }

  public Elements findPosts(String url) throws  Exception{
    Document doc = Jsoup.connect(url).get();
    return doc.select(".d_post_content_main  .p_content");
  }

  public LinkedHashSet<User> createUsers(Elements elements) {
    LinkedHashSet<User> ret = new LinkedHashSet<User>();
    for(Element element : elements) {
      User user = getUser(element);
      if(user != null) {
        ret.add(user);
      }
    }
    return ret;
  }

  public LinkedHashSet<User> createUsers(String url) throws Exception {
    return createUsers(findPosts(url));
  }

  public LinkedHashSet<User> createUsersAcrossThread(String baseUrl, int last) throws Exception {
    List<String> urls = pageLinks(baseUrl, last);
    LinkedHashSet<User> ret = new LinkedHashSet<User>();
    for(String url : urls) {
      ret.addAll(createUsers(url));
    }
    return ret;
  }

  public LinkedHashSet<User> createUsersAcrossThread(String baseUrl) throws Exception {
    Document doc = Jsoup.connect(baseUrl).get();
    int last = findLastPage(doc);
    return createUsersAcrossThread(baseUrl, last);
  }

  public void save(LinkedHashSet<User> users, String filePath) throws Exception {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(users);
    FileWriter fw = new FileWriter(filePath);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(json);
    bw.close();
  }

  public static void main(String args[]) throws Exception {
    Map<String, String> postMap = new LinkedHashMap<String, String>();
    postMap.put("光明之龙" ,"http://tieba.baidu.com/p/2240653401");
    postMap.put("降临天使" ,"http://tieba.baidu.com/p/2240659827");
    postMap.put("荣耀巨人" ,"http://tieba.baidu.com/p/2240694513");
    postMap.put("金属巨龙" ,"http://tieba.baidu.com/p/2240663437");
    postMap.put("森林女神" ,"http://tieba.baidu.com/p/2240679124");
    postMap.put("精灵祭司" ,"http://tieba.baidu.com/p/2240685386");
    postMap.put("时空旅者" ,"http://tieba.baidu.com/p/2240690729");

    for(Map.Entry<String, String> entry : postMap.entrySet()) {
      new Thread(new Example(entry.getValue(), "D:\\mk_" + entry.getKey() + ".json")).start();
    }
  }

  public class User {
    private String baiduId;
    private String role;
    private String username;
    private String server;

    public User(String baiduId, String role, String username, String server) {
      this.baiduId = baiduId;
      this.role = role;
      this.username = username;
      this.server = server;
    }

    @Override
    public boolean equals(Object o) {
      return ((User)o).username.equals(username);
    }

    @Override
    public int hashCode() {
      return username.hashCode();
    }

    public String getBaiduId() {
      return baiduId;
    }

    public String getRole() {
      return role;
    }

    public String getUsername() {
      return username;
    }

    public String getServer() {
      return server;
    }
  }

}
