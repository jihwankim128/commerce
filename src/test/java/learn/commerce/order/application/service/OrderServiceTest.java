package learn.commerce.order.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import learn.commerce.common.domain.Money;
import learn.commerce.order.application.port.in.command.OrderCancellation;
import learn.commerce.order.application.port.out.CancelPaymentPort;
import learn.commerce.order.domain.Order;
import learn.commerce.order.domain.OrderRepository;
import learn.commerce.order.domain.vo.OrderId;
import learn.commerce.order.domain.vo.OrderItems;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CancelPaymentPort cancelPaymentPort;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void 주문_취소_후_결제_처리를_요청한_뒤_취소_결과를_저장한다() {
        // given
        OrderId mockOrderId = mock(OrderId.class);
        OrderCancellation mockCommand = mock(OrderCancellation.class);
        when(mockCommand.toOrderId()).thenReturn(mockOrderId);
        when(mockCommand.cancelReason()).thenReturn("취소 사유");

        OrderItems mockOrderItems = mock(OrderItems.class);
        when(mockOrderItems.calculateCanceledAmount()).thenReturn(Money.ZERO);

        Order mockOrder = mock(Order.class);
        when(mockOrder.getPaymentId()).thenReturn("PAYMENT_ID");
        when(mockOrder.getItems()).thenReturn(mockOrderItems);

        when(orderRepository.getByIdWithThrow(mockOrderId)).thenReturn(mockOrder);

        // when
        orderService.cancel(mockCommand);

        // then
        verify(mockOrder, times(1)).cancel(anyList());
        verify(cancelPaymentPort, times(1)).cancelPayment(anyString(), any(Money.class), anyString());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void 주문_ID로_조회된_주문을_확정처리하고_결과를_저장한다() {
        // given
        Order mockOrder = mock(Order.class);
        when(orderRepository.getByIdWithThrow(any(OrderId.class))).thenReturn(mockOrder);

        // when
        orderService.confirm(UUID.randomUUID());

        // then
        verify(mockOrder, times(1)).confirm();
        verify(orderRepository, times(1)).save(mockOrder);
    }
}