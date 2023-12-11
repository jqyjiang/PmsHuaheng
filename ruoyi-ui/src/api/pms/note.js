import request from '@/utils/request'

// 查询订单送货管理列表
export function listNote(query) {
  return request({
    url: '/pms/note/list',
    method: 'get',
    params: query
  })
}

// 查询订单送货管理详细
export function getNote(orderDeliveryNoteId) {
  return request({
    url: '/pms/note/' + orderDeliveryNoteId,
    method: 'get'
  })
}

// 新增订单送货管理
export function addNote(data) {
  return request({
    url: '/pms/note',
    method: 'post',
    data: data
  })
}

// 修改订单送货管理
export function updateNote(data) {
  return request({
    url: '/pms/note',
    method: 'put',
    data: data
  })
}

// 删除订单送货管理
export function delNote(orderDeliveryNoteId) {
  return request({
    url: '/pms/note/' + orderDeliveryNoteId,
    method: 'delete'
  })
}

// 查询采购合同管理列表
export function listManagement(query) {
  return request({
    url: '/pms/OrderManager/listManagement',
    method: 'get',
    params: query
  })
}