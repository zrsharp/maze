package com.zero.maze.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.zero.maze.entity.Player;

@Component
public class PlayerDao {
    
    private static Properties properties = new Properties();
    
    static {
        try (InputStream is = PlayerDao.class.getClassLoader().getResourceAsStream("config/datafile.properties");
                BufferedInputStream bis = new BufferedInputStream(is)) {
            properties.load(bis);
            System.err.println(properties.getProperty("path"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean savePlayerInfo(Player player) {
        String pathname = properties.getProperty("path") + player.getPlayerName() + ".maze";
        File file = new File(pathname);
        try (FileOutputStream out = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(out);) {
            oos.writeObject(player);
            oos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Player loadPlayerInfo(String playerName) {
        String pathname = properties.getProperty("path") + playerName + ".maze";
        File file = new File(pathname);
        if (!file.exists()) {
            return null;
        }
        
        Player player = null;
        try (FileInputStream in = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(in);) {
            player = (Player) ois.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } 
        
        return player;
    }
    
}
