package com.gridstudentpractice.chatservice.repository.jdbc;

import com.gridstudentpractice.chatservice.exception.RepositoryException;
import com.gridstudentpractice.chatservice.model.RoleDto;
import com.gridstudentpractice.chatservice.model.UserDto;
import com.gridstudentpractice.chatservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Profile("jdbc")
@Repository
public class JDBCUserRepositoryImpl implements UserRepository {

    @Autowired
    private Connection connection;

    final static private String addUserSql = "INSERT INTO users (login, password) VALUES (?, ?);";
    final static private String checkUserSql = "SELECT u.* FROM users u WHERE u.login = ? ORDER BY u.id;";
    final static private String updateUserLoginAndPasswordSql = "UPDATE users u SET login = ?, password = ? WHERE u.id = ?;";
    final static private String addRoleToUser = "INSERT INTO user_role (user_id, role_id) VALUES (?, ?);";
    final static private String deleteUserSql = "DELETE FROM users u WHERE u.id = ?;";
    final static private String getUserRolesSql = "SELECT r.* FROM roles r " +
            "JOIN (users u JOIN user_role ur ON u.id = ur.user_id) ON r.id = ur.role_id WHERE u.login = ?;";

    @Override
    public void createUser(UserDto userDto) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(addUserSql)) {

            preparedStatement.setString(1, userDto.getLogin());
            preparedStatement.setString(2, userDto.getPassword());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException("UserDto creation error", e);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(addRoleToUser)) {

            preparedStatement.setInt(1, userDto.getId());
            preparedStatement.setInt(2, 1);
            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            throw new RepositoryException("User role creation error", e);
        }
    }

    @Override
    public UserDto getUserByLogin(String userLogin) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                checkUserSql,
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY
        )) {

            preparedStatement.setString(1, userLogin);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return UserDto.builder()
                            .id(rs.getInt("id"))
                            .login(rs.getString("login"))
                            .password(rs.getString("password"))
                            .build();
                } else throw new RepositoryException("No such user") ;

            } catch (SQLException e) {
                throw new RepositoryException("ResultSet error", e);
            }
        } catch (SQLException e) {
            throw new RepositoryException("UserDto reading error", e);
        }
    }

    @Override
    public void updateUserLoginAndPassword(UserDto userDto) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateUserLoginAndPasswordSql)) {

            preparedStatement.setString(1, userDto.getLogin());
            preparedStatement.setString(2, userDto.getPassword());
            preparedStatement.setInt(3, userDto.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException("UserDto login and password update error", e);
        }
    }

    @Override
    public void addRoleToUser(int rId, int uId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(addRoleToUser)) {
            preparedStatement.setInt(1, uId);
            preparedStatement.setInt(2, rId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RepositoryException("UserDto creation error", e);
        }
    }

    @Override
    public void deleteUserById(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteUserSql)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RepositoryException("UserDto delete error", e);
        }
    }

    @Override
    public List<RoleDto> getUserRoles(String userLogin) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                getUserRolesSql,
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY)) {
            preparedStatement.setString(1, userLogin);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                List<RoleDto> roleDtos = new ArrayList<>();
                while (rs.next()) {
                    RoleDto roleDto = RoleDto.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .build();
                    roleDtos.add(roleDto);
                }
                return roleDtos;
            }
            catch (SQLException e) {
                throw new RepositoryException("ResultSet error", e);
            }
        }
        catch (SQLException e) {
            throw new RepositoryException("RoleDto reading error", e);
        }
    }
}
