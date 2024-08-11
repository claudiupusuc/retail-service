package app.order.api;

import app.order.dao.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

  Optional<Order> findByOrderId(String orderId);
}
