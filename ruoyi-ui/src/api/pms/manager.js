import request from '@/utils/request'

// 查询采购订单管理列表
export function listManager(query) {
  return request({
    url: '/pms/manager/list',
    method: 'get',
    params: query
  })
}

// 查询采购订单管理详细
export function getManager(orderId) {
  return request({
    url: '/pms/manager/' + orderId,
    method: 'get'
  })
}

// 新增采购订单管理
export function addManager(data) {
  return request({
    url: '/pms/manager',
    method: 'post',
    data: data
  })
}

// 修改采购订单管理
export function updateManager(data) {
  return request({
    url: '/pms/manager',
    method: 'put',
    data: data
  })
}

// 删除采购订单管理
export function delManager(orderId) {
  return request({
    url: '/pms/manager/' + orderId,
    method: 'delete'
  })
}


// 查询供应商详细列表
export function listSupplier() {
  return request({
    url: '/pms/manager/listSupplier',
    method: 'get',

  })
}

// 查询物料列表
export function listMaterial(query) {
  return request({
    url: '/pms/manager/listMaterial',
    method: 'get',
    params: query
  })
}
