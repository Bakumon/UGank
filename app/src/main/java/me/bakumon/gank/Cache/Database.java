package me.bakumon.gank.Cache;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import me.bakumon.gank.App;
import me.bakumon.gank.entity.Item;

/**
 * Created by mafei on 2016/12/2 15:00.
 *
 * @author mafei
 * @version 1.0.0
 * @class Database
 * @describe 数据库
 */
public class Database {
    private static String DATA_FILE_NAME = "data.db";

    private static Database INSTANCE;

    private File dataFile = new File(App.getInstance().getFilesDir(), DATA_FILE_NAME);
    private Gson gson = new Gson();

    private Database() {
    }

    public static Database getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    public List<Item> readItems() {
        // Hard code adding some delay, to distinguish reading from memory and reading disk clearly
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try {
            Reader reader = new FileReader(dataFile);
            return gson.fromJson(reader, new TypeToken<List<Item>>(){}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void writeItems(List<Item> items) {
        String json = gson.toJson(items);
        try {
            if (!dataFile.exists()) {
                try {
                    dataFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Writer writer = new FileWriter(dataFile);
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        dataFile.delete();
    }
}
