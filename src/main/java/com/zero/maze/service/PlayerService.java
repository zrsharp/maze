package com.zero.maze.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zero.maze.dao.PlayerDao;
import com.zero.maze.dto.BaseResultDto;
import com.zero.maze.entity.Player;

@Service
public class PlayerService {

    @Autowired
    private PlayerDao playerDao;

    public BaseResultDto<String> savePlayerInfo(Player player) {
        BaseResultDto<String> result = new BaseResultDto<String>();
        if (player.getPlayerName().length() > 20 || player.getPlayerName().contains(" ")) {
            result.setSuccess(false);
            result.setData(null);
            result.setMessage("用户名不符合规范");
            return result;
        }

        if (playerDao.savePlayerInfo(player)) {
            result.setSuccess(true);
            result.setMessage("用户信息保存成功");
            result.setData(player.getPlayerName());
        } else {
            result.setSuccess(false);
            result.setMessage("用户信息保存失败");
            result.setData(player.getPlayerName());
        }
        return result;
    }

    public BaseResultDto<Player> loadPlayerInfo(String playerName) {
        BaseResultDto<Player> result = new BaseResultDto<Player>();

        if (playerName.length() > 20 || playerName.contains(" ")) {
            result.setSuccess(false);
            result.setData(null);
            result.setMessage("用户名不符合规范");
            return result;
        }

        Player player = playerDao.loadPlayerInfo(playerName);
        if (player == null) {
            result.setSuccess(false);
            result.setData(null);
            result.setMessage("用户不存在");
        } else {
            result.setSuccess(true);
            result.setData(player);
            result.setMessage("用户查询成功");
        }
        return result;
    }
}
