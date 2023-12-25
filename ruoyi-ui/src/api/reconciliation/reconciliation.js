import request from '@/utils/request'

// 查询订单业务对账列表
export function listReconciliation(query) {
  return request({
    url: '/pms/orderDetail/selectReconciliation',
    method: 'get',
    params: query
  })
}
// 查询mingxi详细
export function getOrderDetail(id) {
  return request({
    url: '/pms/orderDetail/' + id,
    method: 'get'
  })
}

// 修改mingxi
export function updateOrderDetail(data) {
  return request({
    url: '/pms/orderDetail',
    method: 'put',
    data: data
  })
}


// 修改对账1
export function updateReconciliation1(data) {
  return request({
    url: '/pms/orderDetail/updateReconciliation1',
    method: 'put',
    data: data
  })
}
// 修改对账2
export function updateReconciliation2(ids) {
  return request({
    url: '/pms/orderDetail/updateReconciliation2',
    method: 'put',
    data:ids
  })
}
// 修改对账3
export function updateReconciliation3(ids) {
  return request({
    url: '/pms/orderDetail/updateReconciliation3',
    method: 'put',
    data:ids
  })
}

