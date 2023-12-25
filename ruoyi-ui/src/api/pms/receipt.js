import request from '@/utils/request'

// 查询订单收货管理列表
export function listReceipt(query) {
  return request({
    url: '/pms/receipt/list',
    method: 'get',
    params: query
  })
}

// 查询订单收货管理详细
export function getReceipt(receiptId) {
  return request({
    url: '/pms/receipt/' + receiptId,
    method: 'get'
  })
}

// 用单号编号和送货单号查询订单收货管理详细
export function getDeliveryInfo(orderCode,deliveryNo) {
  return request({
    url: '/pms/receipt/getInfo',
    method: 'get',
    params:{
      orderCode:orderCode,
      deliveryNo:deliveryNo
    }
  })
}
// 新增订单收货管理
export function addReceipt(data) {
  return request({
    url: '/pms/receipt',
    method: 'post',
    data: data
  })
}

// 修改订单收货管理
export function updateReceipt(data) {
  return request({
    url: '/pms/receipt',
    method: 'put',
    data: data
  })
}

// 删除订单收货管理
export function delReceipt(receiptId) {
  return request({
    url: '/pms/receipt/' + receiptId,
    method: 'delete'
  })
}
