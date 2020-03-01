package com.gridstudentpractice.chatservice.repository;

import com.gridstudentpractice.chatservice.DbUtil;
import com.gridstudentpractice.chatservice.exception.RepositoryException;
import com.gridstudentpractice.chatservice.model.Chatroom;
import com.gridstudentpractice.chatservice.model.User;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDBCChatroomRepositoryImpl implements ChatroomRepository {

    final static private String createChatroom = "INSERT INTO chatrooms (name, description) VALUES (?, ?)";
    final static private String getChatroomById = "SELECT c.* FROM chatrooms c WHERE c.id = ?";
    final static private String getChatroomByName = "SELECT c.* FROM chatrooms c WHERE c.name = ?";
    final static private String createUserInChatroom = "INSERT INTO user_chatroom (user_id, chatroom_id) VALUES (?, ?)";



    @Override
    public void createChatroom(Chatroom chatroom) {
        try(PreparedStatement preparedStatement = DbUtil.getConnection().prepareStatement(createChatroom)) {

            preparedStatement.setString(1, chatroom.getName());
            preparedStatement.setString(2, chatroom.getDescription());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException("Chatroom creation error", e);
        }
    }

    @Override
    public Chatroom getChatroomById(int chatroomId) {
        try (PreparedStatement preparedStatement=DbUtil.getConnection().prepareStatement(getChatroomById)) {
            preparedStatement.setInt(1, chatroomId);

            try (ResultSet rs1 = preparedStatement.executeQuery()) {
                Chatroom chatroom = new Chatroom();
                while (rs1.next()) {

                    chatroom.setId(rs1.getInt("id"));
                    chatroom.setName(rs1.getString("name"));
                    chatroom.setDescription(rs1.getString("description"));
                }
                if (!(chatroom.getName() == null || chatroom.getDescription() == null)) {
                    return chatroom;
                } else return null;
            } catch (SQLException e) {
                throw new RepositoryException("ResultSet error", e);
            } finally {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Chatroom reading error", e);
        }
    }

    @Override
    public List<Chatroom> getChatroomsByName(String chatroomName) {
        try (PreparedStatement preparedStatement = DbUtil.getConnection().prepareStatement(getChatroomByName)) {
            preparedStatement.setString(1, chatroomName);

            try (ResultSet rs2 = preparedStatement.executeQuery()) {
                List<Chatroom> chatrooms = new ArrayList<>();
                while (rs2.next()) {
                    Chatroom chatroom = new Chatroom(rs2.getInt("id"), rs2.getString("name"),
                            rs2.getString("description"));
                    if (!(chatroom.getName() == null || chatroom.getDescription() == null)) {
                        chatrooms.add(chatroom);
                    }
                }
                return chatrooms;

            } catch (SQLException e) {
                throw new RepositoryException("ResultSet error", e);
            } finally {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Chatroom reading error", e);
        }
    }

    //TODO: not working yet
    @Override
    public boolean addUserToChatroom(User user, Chatroom chatroom) {
        try(PreparedStatement preparedStatement = DbUtil.getConnection().prepareStatement(createUserInChatroom)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, chatroom.getId());

            if (preparedStatement.executeUpdate() == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Chatroom creation error", e);
        }
        return true;
    }
}
