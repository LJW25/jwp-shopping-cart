package cart.dao;

import cart.domain.user.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcUserDao implements UserDao {
    
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;
    private final RowMapper<User> userRowMapper = (resultSet, rowNum) ->
            new User(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );

    public JdbcUserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_list")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long insert(final User user) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(user);
        return insertActor.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public User findById(final Long id) {
        final String sql = "SELECT * FROM user_list WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, id);
        } catch (final IncorrectResultSizeDataAccessException exception) {
            return null;
        }
    }

    @Override
    public User findByEmail(final String email) {
        final String sql = "SELECT * FROM user_list WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, email);
        } catch (final IncorrectResultSizeDataAccessException exception) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT * FROM user_list ORDER BY id ASC";
        return jdbcTemplate.query(sql, userRowMapper);
    }
}
