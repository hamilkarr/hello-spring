package hello.hello_spring.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import hello.hello_spring.domain.Member;

public class MemoryMemberRepositoryTest {
    
    MemoryMemberRepository repository = new MemoryMemberRepository();
    
    @AfterEach // 각 테스트 실행 후 메모리 초기화
    public void afterEach() {
        repository.clearStore();
    }
    
    @Test
    public void save() {
        // given
        Member member = new Member();
        member.setName("spring");
        
        // when
        Member savedMember = repository.save(member);
        
        // then
        Member foundMember = repository.findById(savedMember.getId()).get();
        assertThat(foundMember).isEqualTo(savedMember);
    }
    
    @Test
    public void findById() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        
        // when
        Member foundMember = repository.findById(member1.getId()).get();
        
        // then
        assertThat(foundMember).isEqualTo(member1);
    }
    
    @Test
    public void findById_NotFound() {
        // given
        Long nonExistentId = 999L;
        
        // when
        Optional<Member> foundMember = repository.findById(nonExistentId);
        
        // then
        assertThat(foundMember).isEmpty();
    }
    
    @Test
    public void findByName() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        
        Member member3 = new Member();
        member3.setName("spring3");
        repository.save(member3);
        
        // when
        Member foundMember = repository.findByName("spring1").get();
        
        // then
        assertThat(foundMember).isEqualTo(member1);
    }
    
    @Test
    public void findByName_NotFound() {
        // given
        String nonExistentName = "nonExistent";
        
        // when
        Optional<Member> foundMember = repository.findByName(nonExistentName);
        
        // then
        assertThat(foundMember).isEmpty();
    }
    
    @Test
    public void findAll() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        
        // when
        List<Member> allMembers = repository.findAll();
        
        // then
        assertThat(allMembers.size()).isEqualTo(2);
        assertThat(allMembers).contains(member1, member2);
    }
    
    @Test
    public void findAll_Empty() {
        // when
        List<Member> allMembers = repository.findAll();
        
        // then
        assertThat(allMembers.size()).isEqualTo(0);
    }
}
