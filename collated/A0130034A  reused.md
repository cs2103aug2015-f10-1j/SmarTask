# A0130034A  reused
###### src\Storage.java
``` java
 */

public class Storage {

    private static final String DEFAULT_FILENAME = "storage.txt";
    private static final String DEFAULT_PATHFILENAME = "filepath.txt";
    private static final String DEFAULT_PATH = Storage.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private static Storage taskOrganiser;

    private File taskFile;
    private File pathFile;
    private BufferedReader reader;
    private PrintWriter writer;
    private Gson gson;

    private Storage() {
        gson = new Gson();
        resetStorageLocation();
    }

    public static Storage getInstance() {
        if (taskOrganiser == null) {
            taskOrganiser = new Storage();
        }
        
        return taskOrganiser;
    }

    private void resetStorageLocation() {
    	pathFile = new File(DEFAULT_PATHFILENAME);
        checkPathFile(pathFile);
 
        if (retrieveSavePath() == null) {
            taskFile = new File(DEFAULT_PATH + File.separator + DEFAULT_FILENAME);
            checkPathFile(taskFile);
        } else {
        	taskFile = new File(retrieveSavePath());
        }
    }
    
    private void checkPathFile(File taskFile) {
        try {
            if (!taskFile.exists()) {
                taskFile.createNewFile();    
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Initialization Methods
    private boolean initializeReader(File taskFile) {
        try {
            reader = new BufferedReader(new FileReader(taskFile));
        } catch (FileNotFoundException e) {
            return false;
        }
        
        return true;
    }
    
    private void closeReader() {
        try {
        	reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // -------------------------------------------------------------------------------------------
    private String retrieveSavePath() {
        String savePath = null;
        initializeReader(pathFile);
        
        try {
            savePath = reader.readLine();
            if (!(savePath == null)) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        closeReader();
        return savePath;
    }

    public ArrayList<Task> retrieveFile() {
        String text;
        ArrayList<Task> taskList = new ArrayList<Task>();
        try {
            if (!initializeReader(taskFile)) {
                return taskList;
            }
            
            while ((text = reader.readLine()) != null) {
                Task task = gson.fromJson(text, Task.class);
                taskList.add(task);
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        
        closeReader();
        
        if (taskList == null || taskList.isEmpty()) {
            taskList = new ArrayList<Task>();
        }
        
        return taskList;
    }

    public void saveToFile(ArrayList<Task> taskList) {
        try {
            writer = new PrintWriter(taskFile, "UTF-8");
            
            for (Task task : taskList) {
                writer.println(gson.toJson(task));
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
         
        writer.close();
    }
    
    public void setSavePath(String inputPath) {
        String savePath;
        FileWriter fw;
        BufferedWriter bw;
        replacePathFile();
        
        try {
            fw = new FileWriter(pathFile);
            bw = new BufferedWriter(fw);
            bw.write(inputPath);
            bw.newLine();
            bw.close();
            initializeReader(pathFile);
            savePath = reader.readLine();
            closeReader();
            
            if (taskFile.renameTo(new File(savePath))) {
            	resetStorageLocation();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void replacePathFile() {
    	PrintWriter pw;
    	
    	 try {
             File tempFile = new File(pathFile.getAbsolutePath());
             pw = new PrintWriter(tempFile);
             pw.print("");
             pw.close();
             pathFile.delete();
             tempFile.renameTo(pathFile);
    	 } catch (IOException e) {
    		 e.printStackTrace();
    	 }
    }
}
```
