import request from '@/utils/request'

// 查询mingxi列表
export function listOrderDetail(query) {
  return request({
    url: '/pms/orderDetail/list',
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

// 新增mingxi
export function addOrderDetail(data) {
  return request({
    url: '/pms/orderDetail',
    method: 'post',
    data: data
  })
}

// 新增mingxi
export function addOrderDetails(data) {
  return request({
    url: '/pms/orderDetail/orderExecution',
    method: 'post',
    data: data
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
// 修改mingxi
export function updateOrderDetails(data) {
  return request({
    url: '/pms/orderDetail/updateEecel',
    method: 'put',
    data: data
  })
}
// 删除mingxi
export function delOrderDetail(id) {
  return request({
    url: '/pms/orderDetail/' + id,
    method: 'delete'
  })
}
