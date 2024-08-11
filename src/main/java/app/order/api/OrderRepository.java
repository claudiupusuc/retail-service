package app.order.api;

import app.order.dao.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

  Optional<Order> findByOrderId(String orderId);

//  @Query("value = {'date': {$gte : ?0, $lte: ?1}}")
//  List<Order> listOrders(LocalDate start, LocalDate end);

  List<Order> findByDateBetween(LocalDate start, LocalDate end);
}
