import request from '@/utils/request'

// 查询产品明细列表
export function listDelivery(query) {
  return request({
    url: '/pms/delivery/list',
    method: 'get',
    params: query
  })
}

// 查询产品明细详细
export function getDelivery(deliveryId) {
  return request({
    url: '/pms/delivery/' + deliveryId,
    method: 'get'
  })
}

// 新增产品明细
export function addDelivery(data) {
  return request({
    url: '/pms/delivery',
    method: 'post',
    data: data
  })
}

// 修改产品明细
export function updateDelivery(data) {
  return request({
    url: '/pms/delivery',
    method: 'put',
    data: data
  })
}

// 删除产品明细
export function delDelivery(deliveryId) {
  return request({
    url: '/pms/delivery/' + deliveryId,
    method: 'delete'
  })
}
