package com.Liam;

import com.aspose.words.Document;
import com.aspose.words.ImportFormatMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class AsposeDemo {
    public static  HashSet<String> errorMsg = new HashSet<>();
    public static HashSet<String> infoMsg = new LinkedHashSet<>();
    private File faceFile;
    private List<File> tagetFiles=new ArrayList<>();

    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\kl\\Desktop\\20240412 wordTool\\srcMulti";
        new AsposeDemo().replaceFacePage(path);

    }

    public void replaceFacePage(String path) throws Exception {
        //collect files
        collectFiles(path);

        //replacing start
        replaceFaceHandle(path);
    }

    public void replaceFaceHandle(String path) throws Exception {
        infoMsg.add("start replacing...");
        for (int i=0;i<tagetFiles.size();i++){
            File targetFile = tagetFiles.get(i);
            //get Face
            Document facePageDoc = new Document(faceFile.getAbsolutePath());
            Document facePage = facePageDoc.extractPages(0, 1);
            int pageCount1 = facePage.getPageCount();

            //peel target face
            Document target = new Document(targetFile.getAbsolutePath());
            int pageCount = target.getPageCount();
            target.extractPages(1,pageCount-1);

            //merge
            File outputDir = new File(path+"\\Output Files");
            if (!outputDir.exists())outputDir.mkdir();
            facePage.appendDocument(target, ImportFormatMode.KEEP_DIFFERENT_STYLES);
            facePage.save(outputDir.getAbsolutePath()+"\\"+targetFile.getName());

            // msg
            infoMsg.add("meger success:"+targetFile.getName());

        }
    }

    private void collectFiles(String path) {
        File file = new File(path);
        for (File dir : file.listFiles()) {
            if(dir.isDirectory()){
                String dirName = dir.getName();
                infoMsg.add("start collecting...");
                for (File taget : dir.listFiles()) {
                    String filename = taget.getName();
                    if (filename.endsWith(".docx")||filename.endsWith(".doc")){
                        if ("Face-page file".equals(dirName)){
                            faceFile = taget;
                            infoMsg.add("Face page is:"+faceFile.getName());
                        }else if("Taget-page files".equals(dirName)){
                            tagetFiles.add(taget);
                            infoMsg.add("target file is:"+taget.getName());
                        }
                    }
                }
            }
        }
        infoMsg.add("collecting finished...\r\n");
    }
}
