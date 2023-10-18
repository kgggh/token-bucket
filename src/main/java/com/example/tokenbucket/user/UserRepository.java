package com.example.tokenbucket.user;

import com.example.tokenbucket.user.User.MemberShipLevel;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private Map<Long, User> store = new ConcurrentHashMap<>();
    private AtomicLong id = new AtomicLong();

    @PostConstruct
    public void save() {
        for (int i = 0; i < 3; i++) {
            long currentId = this.id.incrementAndGet();

            User user = User.builder()
                .id(currentId)
                .name("김아무개"+i)
                .age(i)
                .memberShipLevel(MemberShipLevel.getMemberShipLevel(i))
                .build();

            store.put(user.getId(), user);
        }
    }
}
