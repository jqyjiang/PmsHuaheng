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
export function listSupplier(query) {
  return request({
    url: '/pms/manager/listSupplier',
    method: 'get',
    params: query
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

// 查询订单物料明细列表
export function listOrderMaterial() {
  return request({
    url: '/pms/manager/listOrderMaterial',
    method: 'get',
  })
}

// 查询币种列表
export function listCurrency(query) {
  return request({
    url: '/pms/manager/listCurrency',
    method: 'get',
    params: query
  })
}

// 查询品类列表
export function listCategory(query) {
  return request({
    url: '/pms/manager/listCategory',
    method: 'get',
    params: query
  })
}