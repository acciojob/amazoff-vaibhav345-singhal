package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    HashMap<String, Order> orderHashMap;
    HashMap<String, DeliveryPartner> partnerHashMap;

    HashMap<String, List<String>> orderPartner;

    HashMap<String, Order> unAssignMap;

    public OrderRepository() {
        orderHashMap = new HashMap<>();
        partnerHashMap = new HashMap<>();
        orderPartner = new HashMap<>();
        unAssignMap = new HashMap<>();
    }

    public void addOrder(Order order) {
        orderHashMap.put(order.getId(), order);
        unAssignMap.put(order.getId(), order);
    }

    public void addPartner(DeliveryPartner partner) {
        partnerHashMap.put(partner.getId(), partner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        List<String> list;
        if (orderHashMap.containsKey(orderId) && partnerHashMap.containsKey(partnerId)) {
            list = orderPartner.get(partnerId);
        } else {
            list = new ArrayList<>();
        }
        list.add(orderId);
        unAssignMap.remove(orderId);
        orderPartner.put(partnerId, list);
    }

    public Order getOrderById(String orderId) {
        return orderHashMap.getOrDefault(orderId, null);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerHashMap.getOrDefault(partnerId, null);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        int count = 0;
        if (orderPartner.containsKey(partnerId)) count = orderPartner.get(partnerId).size();
        return count;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderPartner.getOrDefault(partnerId, null);
    }

    public List<String> getAllOrders() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Order> map : orderHashMap.entrySet()) {
            list.add(map.getKey());
        }
        return list;
    }

    public Integer getCountOfUnassignedOrders() {
        int count = 0;
        count = unAssignMap.size();
        return count;
    }

    public void deletePartnerById(String partnerId) {
        if (partnerHashMap.containsKey(partnerId)) partnerHashMap.remove(partnerId);
        if (orderPartner.containsKey(partnerId)) {
            for (String oId : orderPartner.get(partnerId)) {
                unAssignMap.put(oId, orderHashMap.get(oId));
            }
        }
        orderPartner.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        List<String> list;
        if (unAssignMap.containsKey(orderId)) {
            unAssignMap.remove(orderId);
            return;
        }
        for (Map.Entry<String, List<String>> map : orderPartner.entrySet()) {
            list = map.getValue();
            if (list.contains(orderId)) {
                list.remove(orderId);
            }
        }
        if (orderHashMap.containsKey(orderId)) orderHashMap.remove(orderId);
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int count = 0;
        int hh = Integer.parseInt(time.split(":")[0]) * 60;
        int mm = Integer.parseInt(time.split(":")[1]);

        int t = hh + mm;

        for (String oid : orderPartner.get(partnerId)) {
            if (orderHashMap.get(oid).getDeliveryTime() > t) count++;
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> list = orderPartner.get(partnerId);
        int LastOid = Integer.MIN_VALUE;

        for (String oid : list) {
            LastOid = Math.max(LastOid, orderHashMap.get(oid).getDeliveryTime());
        }

        return orderHashMap.get(LastOid).getOriginalTime();
    }
}
