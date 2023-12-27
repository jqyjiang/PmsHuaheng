import request from '@/utils/request'

// 查询mingxi列表
export function listOrderDetail(query) {
  return request({
    url: '/pms/orderDetail/list',
    method: 'get',
    params: query
  })
}
// 查询送货管理列表
export function listOrderDelivery(query) {
  return request({
    url: '/pms/orderDelivery/list',
    method: 'get',
    params: query
  })
}
// 查询收货单管理列表
export function listOrderReceipt(query) {
  return request({
    url: '/pms/orderDetail/receiptList',
    method: 'get',
    params: query
  })
}

// 查询待收货管理列表
export function stockInList(query) {
  return request({
    url: '/pms/orderDetail/stockInList',
    method: 'get',
    params: query
  })
}

// 查询订单执行明细中的数据根据订单编号和收货单号
export function listOrderQuanlity(orderCode,receiptNo) {
  return request({
    url: '/pms/orderDetail/orderQuanlity',
    method: 'get',
    params:{
      orderCode:orderCode,
      receiptNo:receiptNo
    }
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
