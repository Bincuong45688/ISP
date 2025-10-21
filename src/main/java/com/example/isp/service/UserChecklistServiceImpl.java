package com.example.isp.service;

import com.example.isp.repository.UserChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserChecklistServiceImpl implements UserChecklistService {

    private final UserChecklistRepository repo;

    @Override
    public UserChecklist createChecklist(UserChecklist checklist) {
        return repo.save(checklist);
    }

    @Override
    public List<UserChecklist> getByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    @Override
    public void deleteChecklist(Long id) {
        repo.deleteById(id);
    }
}
