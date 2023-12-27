import request from '@/utils/request'

// 查询订单入库管理列表
export function listStockIn(query) {
  return request({
    url: '/pms/stockIn/list',
    method: 'get',
    params: query
  })
}

// 查询订单入库管理详细
export function getStockIn(id) {
  return request({
    url: '/pms/stockIn/' + id,
    method: 'get'
  })
}

// 查询订单入库管理详细
export function getInboundMaterial(orderCode,orName) {
  return request({
    url: '/pms/stockIn/getInboundMaterial' ,
    method: 'get',
    params:{
       orderCode,
        orName
    }
  })
}

// 新增订单入库管理
export function addStockIn(data) {
  return request({
    url: '/pms/stockIn',
    method: 'post',
    data: data
  })
}

// 修改订单入库管理
export function updateStockIn(data) {
  return request({
    url: '/pms/stockIn',
    method: 'put',
    data: data
  })
}

// 删除订单入库管理
export function delStockIn(id) {
  return request({
    url: '/pms/stockIn/' + id,
    method: 'delete'
  })
}
