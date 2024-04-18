package com.example.springbasic2.repository;

import com.example.springbasic2.constant.ItemSellStatus;
import com.example.springbasic2.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

//JPA에서는 Repository 클래스가 Model 역할(데이터베이스와 대화)을 한다.
//Repository 클래스는 JpaRepository<사용할 엔티티 클래스, 해당 엔티티 PK 타입> 인터페이스를 반드시 상속받아야 한다.
public interface ItemRepository extends JpaRepository<Item, Long> {
    //select * from item where item_nm = ?
    List<Item> findByItemNm(String itemNm);

    //select * from item where item_nm = ? and item_sell_status = ?
    //퀴즈 1-1
    List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus itemSellStatus);

    //매개변수 이름은 엔티티 클래스의 필드명과 꼭 똑같이 작성하지 않아도 된다.
    //JPA에서는 매개변수의 순서대로 쿼리 물음표에 값을 바인딩 한다.
    //select * from item where price between ? and ?

    List<Item> findByPriceBetween(int price1, int price2);
    List<Item> findByRegTimeAfter(LocalDateTime time);
    List<Item> findByItemSellStatusNotNull();
    List<Item> findByItemDetailLike(String string);
    List<Item> findByItemNmOrItemDetail(String name, String detail );
    List<Item> findByPriceLessThanOrderByPriceDesc(int price);

    //JPQL 쿼리(findBy 메소드로 이름을 짓지 않아도 된다.)
    //select * from item where item_detail = ? (일반 쿼리문은 테이블 기준)

//    @Query("select i from Item i where i.itemDetail = ?1 and i.itemNm = ?2")
//    @Query("select i from Item i where i.itemDetail like %?1% order by i.price desc")

//    @Query("select i from Item i where i.itemDetail = :itemDetail")
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query("select i from Item i where i.price >= :price")
    List<Item> getPrice(@Param("price") int price);

    @Query("select i from Item i where i.itemNm = :itemNm and i.itemSellStatus = :itemSellStatus")
    List<Item> getItemNmAndItemSellStatus(@Param("itemNm") String itemNm, @Param("itemSellStatus") ItemSellStatus itemSellStatus);

    @Query("select i from Item i where i.id = :id")
    List<Item> getId(@Param("id") Long id);

    //native query
    @Query(value="select * from item  where item_detail like %:itemDetail% order by price desc ", nativeQuery = true)
    List<Item> findByItemDetailByNatvie(@Param("itemDetail") String itemDetail);
}
