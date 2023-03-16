package main.service;

import main.configs.jwt.JwtUtils;
import main.model.EProduct;
import main.model.Order;
import main.model.ProductType;
import main.pojo.OrderRequest;
import main.pojo.OrderResponse;
import main.pojo.OrderResponseModel;
import main.repository.OrderRepository;
import main.repository.UserRepository;
import main.repository.datarepository.ComponentRepository;
import main.repository.datarepository.LiquidRepository;
import main.repository.datarepository.ModRepository;
import main.repository.datarepository.VapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private LiquidRepository liquidRepository;

    @Autowired
    private ModRepository modRepository;

    @Autowired
    private VapeRepository vapeRepository;


    public OrderResponse getData(String token) {
        String userNameFromToken = jwtUtils.getUserNameFromJwtToken(token.split(" ")[1]);
        List<OrderResponseModel> orderResponseModels = new ArrayList<>();

        for (int i = 0; i < orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).size(); i++) {
            if(orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).get(i).getProductType().getName() == EProduct.COMPONENT) {
                orderResponseModels.add(new OrderResponseModel(
                        componentRepository.findByIdAllIgnoreCase(orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).get(i).getProductId()),
                        orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).get(i).getCount()));
            }
            if(orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).get(i).getProductType().getName() == EProduct.LIQUID) {
                orderResponseModels.add(new OrderResponseModel(
                        liquidRepository.findByIdAllIgnoreCase(orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).get(i).getProductId()),
                        orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).get(i).getCount()));
            }
            if(orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).get(i).getProductType().getName() == EProduct.MOD) {
                orderResponseModels.add(new OrderResponseModel(
                        modRepository.findByIdAllIgnoreCase(orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).get(i).getProductId()),
                        orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).get(i).getCount()));
            }
            if(orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).get(i).getProductType().getName() == EProduct.VAPE) {
                orderResponseModels.add(new OrderResponseModel(
                        vapeRepository.findByIdAllIgnoreCase(orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).get(i).getProductId()),
                        orderRepository.findByUser(userRepository.findByUsernameIgnoreCase(userNameFromToken)).get(i).getCount()));
            }
        }
        return new OrderResponse(orderResponseModels);
    }

    public boolean addOrder(OrderRequest orderRequest) {
        if(orderRepository.existsByUserAndProductIdAndProductType(orderRequest.getUser(), orderRequest.getProductId(), orderRequest.getProductType())) {
            return false;
        } else {
            orderRepository.save(new Order(
                    orderRequest.getUser(),
                    orderRequest.getProductId(),
                    orderRequest.getCount(),
                    orderRequest.getProductType()));
            return true;
        }
    }

    public void changeOrder(OrderRequest orderRequest) {
        if(orderRepository.existsByUserAndProductIdAndProductType(orderRequest.getUser(), orderRequest.getProductId(), orderRequest.getProductType())) {
            orderRepository.findByUserAndProductIdAndProductType(
                    orderRequest.getUser(),
                    orderRequest.getProductId(),
                    orderRequest.getProductType())
                    .setCount(orderRequest.getCount());
            orderRepository.save(orderRepository.findByUserAndProductIdAndProductType(
                    orderRequest.getUser(),
                    orderRequest.getProductId(),
                    orderRequest.getProductType()));
        }
    }

    public void deleteOrder(OrderRequest orderRequest) {
        if(orderRepository.existsByUserAndProductIdAndProductType(orderRequest.getUser(), orderRequest.getProductId(), orderRequest.getProductType())) {
            orderRepository.deleteById(orderRepository.findByUserAndProductIdAndProductType(
                    orderRequest.getUser(),
                    orderRequest.getProductId(),
                    orderRequest.getProductType()).getId());
        }
    }
}
