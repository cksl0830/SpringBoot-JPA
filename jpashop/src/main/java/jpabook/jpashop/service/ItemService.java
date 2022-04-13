package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     변경 감지 :: 원하는 속성만 선택해서 변경가능 (영속성 컨텍스트에서 엔티티 다시조회 -> 데이터 수정)
     merge 방법 :: 모든 속성 변경!! (값이 없으면 null로 업데이트)
                  준영속 상태의 엔티티를 영속상태로 변경 해버림
     결론 :: 트랜잭션 안에서 엔티티를 조회해야 영속상태로 조회 -> 여기서 변경해야 변경감지 일어남 (commit 할 때)
        -> 데이터베이스에 업데이트 실행 :: 즉, 변경감지를 사용하자!!!!
     */

    // Setter 사용하지 말고 메서드 만들어서 역추적 가능하게 짜자!!!
    // 추가적으로 넘길 파라미터가 많으면, DTO 생성해서 넘기면 됨
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item item = itemRepository.findOne(itemId);
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
