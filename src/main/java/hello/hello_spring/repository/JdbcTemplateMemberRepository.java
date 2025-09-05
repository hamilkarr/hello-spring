package hello.hello_spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

import hello.hello_spring.domain.Member;

@Repository 
public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Override
    public Member save(Member member) {
        // GeneratedKeyHolder를 사용하여 자동 생성된 ID를 안전하게 가져오기
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement("INSERT INTO member (name) VALUES (?)", 
                java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, member.getName());
            return ps;
        }, keyHolder);
        
        // 생성된 ID를 설정
        var key = keyHolder.getKey();
        if (key != null) {
            member.setId(key.longValue());
        }
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("SELECT * FROM member WHERE id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }
    
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("SELECT * FROM member WHERE name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }
    
    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
