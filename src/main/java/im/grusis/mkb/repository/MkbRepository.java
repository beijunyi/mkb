package im.grusis.mkb.repository;

import java.io.*;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: beij
 * Date: 23/01/13
 * Time: 15:13
 */

public abstract class MkbRepository<T> {

  private static final Logger Log = LoggerFactory.getLogger(MkbRepository.class);

  private static final String UserHome = "user.home";
  private static final String LineSeparator = "line.separator";
  private static final String MkbRepositoryDirectoryName = ".mkb";
  private static final String FileSuffix = ".mkb";

  protected Gson gson = new Gson();
  protected String repoFolder;
  protected String separator;

  protected String childFolder;

  protected MkbRepository(String childFolder) {
    this.childFolder = childFolder;
    String userHomeDirectory = System.getProperty(UserHome);
    String mkbRepositoryDirectory = userHomeDirectory + '/' + MkbRepositoryDirectoryName;
    File repo = new File(mkbRepositoryDirectory);
    if(repo.isFile() || !repo.exists() && !repo.mkdirs()) {
      Log.error("Cannot create repository in {}.", mkbRepositoryDirectory);
      System.exit(1);
    }
    repoFolder = repo.getAbsolutePath();
    separator = System.getProperty(LineSeparator);
    if(separator == null || separator.isEmpty()) {
      separator = "\n";
    }
  }

  protected void write(String index, Object obj, boolean overwrite) {
    String fileName = repoFolder + '/' + childFolder + '/' + index;
    File file = new File(fileName + FileSuffix);
    int count = 1;
    while(file.exists()) {
      file = new File(fileName + '_' + count + FileSuffix);
    }
    String jsonString = gson.toJson(obj);
    try {
      if(!file.createNewFile()) {
        Log.error("Cannot create file {}", file.getAbsolutePath());
        return;
      }
      FileWriter fw = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(jsonString);
      bw.close();
      if(overwrite && !file.getName().equalsIgnoreCase(index + FileSuffix)) {
        File oldFile = new File(fileName + FileSuffix);
        if(!oldFile.delete()) {
          Log.error("Cannot delete file {}", oldFile.getAbsolutePath());
          return;
        }
        if(!file.renameTo(oldFile)) {
          Log.error("Cannot rename file from {} to {}", file.getAbsolutePath(), oldFile.getCanonicalPath());
        }
      }
    } catch(Exception e) {
      Log.error("Cannot write file {}", file.getAbsolutePath());
    }
  }

  public T read(String index, Class<T> clazz) {
    String fileName = repoFolder + '/' + childFolder + '/' + index + FileSuffix;
    File file = new File(fileName);
    if(!file.exists()) {
      Log.error("Cannot find file {}", fileName);
      return null;
    }
    try {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      StringBuilder sb = new StringBuilder();
      String line;
      while((line = br.readLine()) != null) {
        sb.append(line).append(separator);
      }
      String string = sb.toString();
      return gson.fromJson(string, clazz);
    } catch(Exception e) {
      Log.error("Cannot read file {}", fileName);
      return null;
    }
  }

}
