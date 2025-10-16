package com.example.isp.service;

import com.example.isp.model.UserChecklistItem;
import com.example.isp.repository.UserChecklistItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserChecklistItemServiceImpl implements UserChecklistItemService {

    private final UserChecklistItemRepository itemRepository;

    @Override
    public UserChecklistItem addItem(UserChecklistItem item) {
        return itemRepository.save(item);
    }

    @Override
    public List<UserChecklistItem> getItemsByChecklist(Long checklistId) {
        return itemRepository.findByUserChecklist_Id(checklistId);
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
